package com.api.ovgs.repository;

import com.api.ovgs.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ovgs.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends  JpaRepository<Item, Integer>{

    boolean existsBySku(String sku);
}
