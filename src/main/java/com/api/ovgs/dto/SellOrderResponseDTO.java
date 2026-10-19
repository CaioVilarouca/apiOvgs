package com.api.ovgs.dto;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SellOrderResponseDTO {

    private Integer sellOrderId;

    private SellOrderStatus sellOrderStatus;

    private String clientName;

    private String typeTransportName;

    private LocalDate scheduling;

    public SellOrderResponseDTO(SellOrder sellOrder) {
        this.sellOrderId = sellOrder.getId();
        this.sellOrderStatus = sellOrder.getSellOrderStatus();
        this.clientName = sellOrder.getClient().getName();
        this.typeTransportName = sellOrder.getTypeTransport().getName();
        this.scheduling = sellOrder.getScheduling();
    }

    public Integer getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(Integer sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public SellOrderStatus getSellOrderStatus() {
        return sellOrderStatus;
    }

    public void setSellOrderStatus(SellOrderStatus sellOrderStatus) {
        this.sellOrderStatus = sellOrderStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTypeTransportName() {
        return typeTransportName;
    }

    public void setTypeTransportName(String typeTransportName) {
        this.typeTransportName = typeTransportName;
    }

    public LocalDate getScheduling() {
        return scheduling;
    }

    public void setScheduling(LocalDate scheduling) {
        this.scheduling = scheduling;
    }
}