package com.api.ovgs.dto;

import com.api.ovgs.entity.Client;

public class ClientResquestDTO {

    private String name;

    private String email;

    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client toEntity(){
        return new Client(null, this.name, this.email, this.active);
    }
}
