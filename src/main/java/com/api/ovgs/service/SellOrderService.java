package com.api.ovgs.service;

import com.api.ovgs.dto.SellOrderResponseDTO;
import com.api.ovgs.dto.SellOrderResquetDTO;
import com.api.ovgs.entity.*;
import com.api.ovgs.repository.ClientRepository;
import com.api.ovgs.repository.SellOrderRepository;
import com.api.ovgs.repository.TypeTransportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

        Client clientId = clientRepository.findById(sellOrderResquetDTO.getClientId()).get();
        TypeTransport typeTransportId = typeTransportRepository.findById(sellOrderResquetDTO.getTypeTransportId()).get();

        if (!clientId.isActive()) {
            throw new IllegalArgumentException("O cliente não pode ter um cadastro inativo ["+clientId.isActive()+"]");
        }

        if (!typeTransportId.isActive()) {
            throw new IllegalArgumentException("O trasporte não pode ter um cadastro inativo ["+typeTransportId.isActive()+"]");
        }

        //Scheduling schedulingId = schedulingService.findById(sellOrderResquetDTO.getSchedulingId()).get();

        // Repository recebe a entidade
        SellOrder resquet = sellOrderResquetDTO.toEntity(clientId, typeTransportId);
        // Repository recebe a entidade
        SellOrder sellOrder = sellOrderRepository.save(resquet);
        // Conversão Entidade para DTO
        return new SellOrderResponseDTO(sellOrder);
    }
}
