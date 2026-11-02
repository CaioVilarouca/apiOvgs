package com.api.ovgs.dto;

import com.api.ovgs.entity.Client;
import jakarta.validation.constraints.NotNull;

public class ClientResquestDTO {

    private String name;

    private String email;

    @NotNull(message = "O campo active é obrigatório")
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Client toEntity(boolean activeTrue){
        return new Client(null, this.name, this.email, activeTrue);
    }

}
