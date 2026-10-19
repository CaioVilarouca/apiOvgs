package com.api.ovgs.dto;

import com.api.ovgs.domain.SchedulingStatus;
import com.api.ovgs.entity.Scheduling;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SchedulingResquetDTO {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDelivery;


    public LocalDate getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(LocalDate dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public Scheduling toEntity() {
        return new Scheduling(null, this.dateDelivery, SchedulingStatus.PENDENTE.getKey());
    }
}
