package com.api.ovgs.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tb_ItemMove" +
        "")
public class ItemMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amounts;

    private Integer itemId;

    public ItemMove() {}

    public ItemMove(Integer id, Integer amounts, Integer itemId) {
        this.id = id;
        this.amounts = amounts;
        this.itemId = itemId;
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
