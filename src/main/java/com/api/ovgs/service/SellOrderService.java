package com.api.ovgs.service;

import com.api.ovgs.dto.SellOrderResponseDTO;
import com.api.ovgs.dto.SellOrderResquetDTO;
import com.api.ovgs.entity.*;
import com.api.ovgs.exception.FindByIdException;
import com.api.ovgs.repository.ClientRepository;
import com.api.ovgs.repository.ItemRepository;
import com.api.ovgs.repository.SellOrderRepository;
import com.api.ovgs.repository.TypeTransportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;


@Service
public class SellOrderService {

    private final SellOrderRepository sellOrderRepository;
    private final ClientRepository clientRepository;
    private final TypeTransportRepository typeTransportRepository;

    public SellOrderService(SellOrderRepository sellOrderRepository, ClientRepository clientRepository, TypeTransportRepository typeTransportRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.clientRepository = clientRepository;
        this.typeTransportRepository = typeTransportRepository;
    }

    @Transactional
    public SellOrderResponseDTO createSellOrder(SellOrderResquetDTO sellOrderResquetDTO){
        try {

            // ---------- CLIENTE ----------
            Client clientId = clientRepository.findById(sellOrderResquetDTO.getClientId()).get();
            if (!clientId.isActive()) {
                throw new IllegalArgumentException("O cliente não pode ter um cadastro inativo [" + clientId.isActive() + "]");
            }

            // ---------- TIPO DE TRASPORTE ----------
            TypeTransport typeTransportId = typeTransportRepository.findById(sellOrderResquetDTO.getTypeTransportId()).get();
            if (!typeTransportId.isActive()) {
                throw new IllegalArgumentException("O trasporte não pode ter um cadastro inativo [" + typeTransportId.isActive() + "]");
            }

            // ---------- AGENDAMENTO ----------
            if (sellOrderResquetDTO.getScheduling().isBefore(LocalDate.now())){
                throw new IllegalArgumentException("A data de agendamento não pode ser anterior à data atual. Data inválida:" + sellOrderResquetDTO.getScheduling());
            }

            // Repository recebe a entidade
            SellOrder resquet = sellOrderResquetDTO.toEntity(clientId, typeTransportId);
            // Repository recebe a entidade
            SellOrder sellOrder = sellOrderRepository.save(resquet);
            // Conversão Entidade para DTO
            return new SellOrderResponseDTO(sellOrder);
        }
        catch (NoSuchElementException e) {
            throw new FindByIdException("ID não encontrado");
        }
    }
}