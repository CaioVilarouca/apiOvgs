
package com.api.ovgs.service;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.dto.SellOrderItemResquestDTO;
import com.api.ovgs.dto.SellOrderResponseDTO;
import com.api.ovgs.dto.SellOrderResquetDTO;
import com.api.ovgs.entity.*;
import com.api.ovgs.exception.BusinessRuleException;
import com.api.ovgs.exception.ObjectNotFoundException;
import com.api.ovgs.exception.RegistrationFailedException;
import com.api.ovgs.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SellOrderService {

    private final SellOrderRepository sellOrderRepository;
    private final ClientRepository clientRepository;
    private final TypeTransportRepository typeTransportRepository;
    private final ItemRepository itemRepository;


    public SellOrderService(
            SellOrderRepository sellOrderRepository,
            ClientRepository clientRepository,
            TypeTransportRepository typeTransportRepository,
            ItemRepository itemRepository) {

        this.sellOrderRepository = sellOrderRepository;
        this.clientRepository = clientRepository;
        this.typeTransportRepository = typeTransportRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public SellOrderResponseDTO createSellOrder(SellOrderResquetDTO sellOrderResquetDTO) {

        // ---------- CLIENTE ----------
        Client client = clientRepository
                .findById(sellOrderResquetDTO.getClientId())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado "));

        if (!client.isActive()) {
            throw new RegistrationFailedException("O cliente {"+client.getName()+"} está inativo. Ative o cadastro para realizar o pedido");
        }

        // ---------- AGENDAMENTO ----------
        if (sellOrderResquetDTO.getScheduling().isBefore(LocalDate.now())) {
            throw new BusinessRuleException("A data de agendamento não pode ser anterior à data {"+LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"}. Informe uma data válida");
        }

        // ---------- TRANSPORTE ----------
        TypeTransport transport = typeTransportRepository
                .findById(sellOrderResquetDTO.getTypeTransportId())
                .orElseThrow(() -> new ObjectNotFoundException("Transporte não encontrado"));

        if (!transport.isActive()) {
            throw new RegistrationFailedException("O trasporte {"+ transport.getName()+"} está inativo");
        }

        // ---------- ORDEM DE VENDA ----------
        SellOrder newSellOrder = new SellOrder();
        newSellOrder.setScheduling(sellOrderResquetDTO.getScheduling());
        newSellOrder.setSellOrderStatus(SellOrderStatus.CRIADO);
        newSellOrder.setClient(client);
        newSellOrder.setTypeTransport(transport);

        // Agrupa entradas do mesmo produto para evitar ultrapassar o estoque.
        Map<Integer, Integer> requestedQuantities = new LinkedHashMap<>();
        for (SellOrderItemResquestDTO itemDTO : sellOrderResquetDTO.getItemList()) {
            if (itemDTO == null || itemDTO.getItemId() == null) {
                throw new RegistrationFailedException("O ID do item é obrigatório");
            }
            Integer quantity = itemDTO.getAmounts();
            if (quantity == null || quantity <= 0) {
                throw new RegistrationFailedException("Quantidade inválida para o item: " + itemDTO.getItemId());
            }
            try {
                requestedQuantities.merge(itemDTO.getItemId(), quantity, Math::addExact);
            } catch (ArithmeticException ex) {
                throw new RegistrationFailedException("Quantidade total excede o limite permitido "+ ex);
            }
        }

        // Adquire bloqueios sempre pela ordem crescente dos IDs, diminuindo
        // o risco de deadlock caso vendas simultâneas tenham os mesmos produtos.
        Map<Integer, Item> lockedItems = new HashMap<>();
        for (Integer itemId : requestedQuantities.keySet().stream().sorted().toList()) {
            Item item = itemRepository.findByIdForUpdate(itemId)
                    .orElseThrow(() -> new ObjectNotFoundException("Item não encontrado: " + itemId));
            Integer quantity = requestedQuantities.get(itemId);

            if (!item.isActive()) {
                throw new RegistrationFailedException("Item inativo: " + item.getName());
            }
            if (item.getStock() == null || item.getStock() < quantity) {
                throw new RegistrationFailedException("Estoque insuficiente para: " + item.getName());
            }
            lockedItems.put(itemId, item);
        }

        // Em caso de erro, a transação desfaz tanto a venda quanto as baixas.
        List<SellOrderItem> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> requested : requestedQuantities.entrySet()) {
            Item item = lockedItems.get(requested.getKey());
            Integer quantity = requested.getValue();
            item.setStock(item.getStock() - quantity);

            SellOrderItem orderItem = new SellOrderItem();
            orderItem.setItem(item);
            orderItem.setAmounts(quantity);
            orderItem.setSellOrder(newSellOrder);
            items.add(orderItem);
        }
        newSellOrder.setItemList(items);

        // ---------- SALVAR ----------
        SellOrder saveSellOrder = sellOrderRepository.save(newSellOrder);
        return new SellOrderResponseDTO(saveSellOrder);
    }
}
