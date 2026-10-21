package com.api.ovgs.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tb_ItemMove")
public class ItemMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemMove() {}

    public ItemMove(Integer amount, Item item) {
        this.amount = amount;
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
