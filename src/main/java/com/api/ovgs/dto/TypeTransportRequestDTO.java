package com.api.ovgs.dto;

import com.api.ovgs.entity.TypeTransport;
import jakarta.validation.constraints.NotNull;

public class TypeTransportRequestDTO {

    private String name;

    private String description;

    @NotNull(message = "O campo active é obrigatório. O campo active deve ser true ou false")
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public TypeTransport toEntity() {
        return new TypeTransport(null, this.name, this.description, true);
    }
}
