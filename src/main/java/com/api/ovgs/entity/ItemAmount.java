package com.api.ovgs.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tb_itemAmount")
public class ItemAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "items_id")
    private Item items;

    private Integer amounts;

    public ItemAmount() {}

    public ItemAmount(Integer id, Item items, Integer amounts) {
        this.id = id;
        this.items = items;
        this.amounts = amounts;
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }
}
