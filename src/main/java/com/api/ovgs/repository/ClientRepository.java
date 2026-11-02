package com.api.ovgs.repository;

import com.api.ovgs.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Uma interface para acessar o banco de dados
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByEmail(String email);

    List<Client> findByActive(boolean active);
}
