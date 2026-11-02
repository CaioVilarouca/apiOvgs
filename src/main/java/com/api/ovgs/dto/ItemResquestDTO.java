package com.api.ovgs.dto;

import com.api.ovgs.entity.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemResquestDTO {

    private String name;

    private String description;

    private String sku;

    @NotNull(message = "O campo active é obrigatório. O campo active deve ser true ou false")
    private Boolean active;

    @Min(value = 1, message = "O produto deve ter preço")
    private Double price;

    @Min(value = 0, message = "O estoque não pode ser negativo")
    private Integer stock;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Item itemEntity() {
        return new Item(null, this.name,this.description, this.sku, true, this.price, this.stock);
    }
}
