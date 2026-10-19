package com.api.ovgs.dto;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class SellOrderResquetDTO {

    private Integer clientId;

    private Integer typeTransportId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate scheduling;

    private List<ItemAmount> list;


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

    public LocalDate getScheduling() {
        return scheduling;
    }

    public void setScheduling(LocalDate scheduling) {
        this.scheduling = scheduling;
    }

    public List<ItemAmount> getList() {
        return list;
    }

    public void setList(List<ItemAmount> list) {
        this.list = list;
    }

    public SellOrder toEntity(Client client, TypeTransport typeTransport) {
        return new SellOrder(null, SellOrderStatus.CRIADO.getKey(), this.scheduling, client, typeTransport, null);
    }
}
