
package com.api.ovgs.service;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.dto.SellOrderItemResquestDTO;
import com.api.ovgs.dto.SellOrderResponseDTO;
import com.api.ovgs.dto.SellOrderResquetDTO;
import com.api.ovgs.entity.*;
import com.api.ovgs.exception.FindByIdException;
import com.api.ovgs.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public SellOrderResponseDTO createSellOrder(SellOrderResquetDTO dto) {

        if (dto == null || dto.getClientId() == null || dto.getTypeTransportId() == null) {
            throw new IllegalArgumentException("Cliente e transporte são obrigatórios");
        }

        // ---------- CLIENTE ----------
        Client client = clientRepository
                .findById(dto.getClientId())
                .orElseThrow(() -> new FindByIdException("Cliente não encontrado"));

        if (!client.isActive()) {
            throw new IllegalArgumentException("O cliente está inativo");
        }

        // ---------- TRANSPORTE ----------
        TypeTransport transport = typeTransportRepository
                .findById(dto.getTypeTransportId())
                .orElseThrow(() -> new FindByIdException("Transporte não encontrado"));

        if (!transport.isActive()) {
            throw new IllegalArgumentException("O transporte está inativo");
        }

        // ---------- AGENDAMENTO ----------
        if (dto.getScheduling() == null || dto.getScheduling().isBefore(LocalDate.now())) {

            throw new IllegalArgumentException("Data de agendamento inválida");
        }

        // ---------- LISTA DE ITENS ----------
        if (dto.getItemList() == null || dto.getItemList().isEmpty()) {

            throw new IllegalArgumentException("A ordem precisa conter pelo menos um item");
        }

        // ---------- ORDEM DE VENDA ----------
        SellOrder order = new SellOrder();

        order.setClient(client);
        order.setTypeTransport(transport);
        order.setScheduling(dto.getScheduling());
        order.setSellOrderStatus(SellOrderStatus.CRIADO);

        // Agrupa entradas do mesmo produto para evitar ultrapassar o estoque.
        Map<Integer, Integer> requestedQuantities = new LinkedHashMap<>();
        for (SellOrderItemResquestDTO itemDTO : dto.getItemList()) {
            if (itemDTO == null || itemDTO.getItemId() == null) {
                throw new IllegalArgumentException("O ID do item é obrigatório");
            }
            Integer quantity = itemDTO.getAmounts();
            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para o item: " + itemDTO.getItemId());
            }
            try {
                requestedQuantities.merge(itemDTO.getItemId(), quantity, Math::addExact);
            } catch (ArithmeticException ex) {
                throw new IllegalArgumentException("Quantidade total excede o limite permitido", ex);
            }
        }

        // Adquire bloqueios sempre pela ordem crescente dos IDs, diminuindo
        // o risco de deadlock caso vendas simultâneas tenham os mesmos produtos.
        Map<Integer, Item> lockedItems = new HashMap<>();
        for (Integer itemId : requestedQuantities.keySet().stream().sorted().toList()) {
            Item item = itemRepository.findByIdForUpdate(itemId)
                    .orElseThrow(() -> new FindByIdException("Item não encontrado: " + itemId));
            Integer quantity = requestedQuantities.get(itemId);

            if (!item.isActive()) {
                throw new IllegalArgumentException("Item inativo: " + item.getName());
            }
            if (item.getStock() == null || item.getStock() < quantity) {
                throw new IllegalArgumentException("Estoque insuficiente para: " + item.getName());
            }
            lockedItems.put(itemId, item);
        }

        // Somente após verificar TODOS os itens é feita a baixa do estoque.
        // Os produtos são entidades gerenciadas pelo JPA; suas alterações são
        // persistidas no commit da mesma transação que salva a ordem.
        // Em caso de erro, a transação desfaz tanto a venda quanto as baixas.
        List<SellOrderItem> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> requested : requestedQuantities.entrySet()) {
            Item item = lockedItems.get(requested.getKey());
            Integer quantity = requested.getValue();
            item.setStock(item.getStock() - quantity);

            SellOrderItem orderItem = new SellOrderItem();
            orderItem.setItem(item);
            orderItem.setAmounts(quantity);
            orderItem.setSellOrder(order);
            items.add(orderItem);
        }

        order.setItemList(items);

        // ---------- SALVAR ----------
        SellOrder savedOrder = sellOrderRepository.save(order);

        return new SellOrderResponseDTO(savedOrder);
    }
}
