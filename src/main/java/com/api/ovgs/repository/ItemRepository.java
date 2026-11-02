package com.api.ovgs.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.api.ovgs.entity.Item;

@Repository
public interface ItemRepository extends  JpaRepository<Item, Integer>{

    boolean existsBySku(String sku);

    // Bloqueia a linha do produto enquanto uma venda verifica e atualiza seu estoque.
    // Isso impede que duas vendas concorrentes consumam o mesmo saldo.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Optional<Item> findByIdForUpdate(@Param("id") Integer id);
}
