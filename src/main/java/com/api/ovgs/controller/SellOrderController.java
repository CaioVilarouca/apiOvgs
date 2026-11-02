package com.api.ovgs.controller;

import com.api.ovgs.dto.SellOrderResquetDTO;
import com.api.ovgs.dto.SellOrderResponseDTO;
import com.api.ovgs.service.SellOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sellOrder")
public class SellOrderController { // Ordem de venda

    private final SellOrderService sellOrderService;

    public SellOrderController(SellOrderService sellOrderService) {
        this.sellOrderService = sellOrderService;
    }

    // Criar uma ordem de venda
    @PostMapping
    public SellOrderResponseDTO create(@RequestBody SellOrderResquetDTO sellOrderResquetDTO){
        return sellOrderService.createSellOrder(sellOrderResquetDTO);
    }
}
