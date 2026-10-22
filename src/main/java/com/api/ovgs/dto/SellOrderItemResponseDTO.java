package com.api.ovgs.dto;

import com.api.ovgs.entity.Item;
import com.api.ovgs.entity.SellOrderItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Dados de cada produto comprado em uma ordem de venda. */
public class SellOrderItemResponseDTO {

    private final String name;
    private final Integer amounts;
    private final BigDecimal unitPrice;
    private final BigDecimal subtotal;

    public SellOrderItemResponseDTO(SellOrderItem sellOrderItem) {
        Item item = sellOrderItem.getItem();

        this.name = item.getName();
        this.amounts = sellOrderItem.getAmounts();
        this.unitPrice = BigDecimal.valueOf(item.getPrice()).setScale(2, RoundingMode.HALF_UP);
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(amounts));
    }

    public String getName() {
        return name;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}