package com.api.ovgs.dto;

import com.api.ovgs.entity.Item;

public class ItemResquestDTO {

    private Integer id;

    private String name;

    private String description;

    private String sku;

    private boolean active;

    private Double price;

    private Integer stock;

    public ItemResquestDTO(Integer id, String name, String description, String sku, boolean active, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.active = active;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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
       return new Item(null, this.name,this.description, this.sku, this.active, this.price, this.stock);
    }
}
