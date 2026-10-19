package com.api.ovgs.dto;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.entity.*;

import java.util.List;

public class SellOrderResquetDTO {

    private Integer clientId;

    private Integer typeTransportId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getTypeTransportId() {
        return typeTransportId;
    }

    public void setTypeTransportId(Integer typeTransportId) {
        this.typeTransportId = typeTransportId;
    }

    public SellOrder toEntity(Client client, TypeTransport typeTransport) {
        return new SellOrder(null, SellOrderStatus.CRIADO.getKey(), client, typeTransport);
    }
}
