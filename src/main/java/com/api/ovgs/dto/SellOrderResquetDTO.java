package com.api.ovgs.dto;

import com.api.ovgs.domain.SellOrderStatus;
import com.api.ovgs.entity.Client;
import com.api.ovgs.entity.TypeTransport;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class SellOrderResquetDTO {

    @NotNull(message = "O campo scheduling é obrigatório. O campo scheduling deve ser 'DD/MM/YYYY'")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate scheduling;

    private SellOrderStatus sellOrderStatus;

    @NotNull(message = "O campo clientId é obrigatório")
    private Integer clientId;

    @NotNull(message = "O campo typeTransportId é obrigatório")
    private Integer typeTransportId;


    public LocalDate getScheduling() {
        return scheduling;
    }

    public void setScheduling(LocalDate scheduling) {
        this.scheduling = scheduling;
    }

    public SellOrderStatus getSellOrderStatus() {
        return sellOrderStatus;
    }

    public void setSellOrderStatus(SellOrderStatus sellOrderStatus) {
        this.sellOrderStatus = sellOrderStatus;
    }

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

    private List<SellOrderItemResquestDTO> itemList;


    public List<SellOrderItemResquestDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<SellOrderItemResquestDTO> itemList) {
        this.itemList = itemList;
    }
}
