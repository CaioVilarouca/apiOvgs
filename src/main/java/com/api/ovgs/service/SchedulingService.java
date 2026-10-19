package com.api.ovgs.service;

import com.api.ovgs.dto.SchedulingReponseDTO;
import com.api.ovgs.dto.SchedulingResquetDTO;
import com.api.ovgs.entity.Scheduling;
import com.api.ovgs.repository.SchedulingRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    public SchedulingService(SchedulingRepository schedulingRepository) {
        this.schedulingRepository = schedulingRepository;
    }

    // Criar agendamento
    public SchedulingReponseDTO createScheduling(@NotNull SchedulingResquetDTO schedulingResquetDTO) {
        if (schedulingResquetDTO.getDateDelivery().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de agendamento não pode ser anterior à data atual. Data inválida:" + schedulingResquetDTO.getDateDelivery());
        }

        Scheduling scheduling = schedulingResquetDTO.toEntity();
        return new SchedulingReponseDTO(schedulingRepository.save(scheduling));
    }
}
