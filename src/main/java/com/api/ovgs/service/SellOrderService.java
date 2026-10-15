package com.api.ovgs.service;

import com.api.ovgs.dto.SellOrderResponseDTO;
import com.api.ovgs.dto.SellOrderResquetDTO;
import com.api.ovgs.entity.*;
import com.api.ovgs.exception.FindByIdException;
import com.api.ovgs.repository.ClientRepository;
import com.api.ovgs.repository.SellOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellOrderService {

    private final SellOrderRepository sellOrderRepository;
    private final ClientRepository clientRepository;

    public SellOrderService(SellOrderRepository sellOrderRepository, ClientRepository clientRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public SellOrderResponseDTO createSellOrder(SellOrderResquetDTO sellOrderResquetDTO){

        Client clientId = clientRepository.findById(sellOrderResquetDTO.getClientId()).get();

        if (!clientId.isActive()) {
            throw new IllegalArgumentException("O cliente não pode ter um cadastro inativo ["+clientId.isActive()+"]");
        }

        //Scheduling schedulingId = schedulingService.findById(sellOrderResquetDTO.getSchedulingId()).get();
        //TypeTransport typeTransportId = typeTransportService.findById(sellOrderResquetDTO.getTypeTransportId()).get();

        // Repository recebe a entidade
        SellOrder resquet = sellOrderResquetDTO.toEntity(clientId);
        // Repository recebe a entidade
        SellOrder sellOrder = sellOrderRepository.save(resquet);
        // Conversão Entidade para DTO
        return new SellOrderResponseDTO(sellOrder);
    }
}
