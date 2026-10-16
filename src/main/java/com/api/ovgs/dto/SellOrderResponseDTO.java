package com.api.ovgs.dto;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.entity.*;

public class SellOrderResponseDTO {

    private Integer id;

    private SellOrderStatus sellOrderStatus;

    private String clientName;

    private String typeTransportName;

    public SellOrderResponseDTO(SellOrder sellOrder) {
        this.id = sellOrder.getId();
        this.sellOrderStatus = sellOrder.getSellOrderStatus();
        this.clientName = sellOrder.getClient().getName();
        this.typeTransportName = sellOrder.getTypeTransport().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
