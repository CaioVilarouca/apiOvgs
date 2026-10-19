package com.api.ovgs.repository;

import com.api.ovgs.entity.SellOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellOrderRepository extends JpaRepository<SellOrder,Integer> {
}
