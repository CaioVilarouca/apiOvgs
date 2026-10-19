package com.api.ovgs.service;

import com.api.ovgs.dto.ItemReponseDTO;
import com.api.ovgs.dto.ItemResquestDTO;
import com.api.ovgs.entity.Item;
import com.api.ovgs.repository.ItemRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemReponseDTO createItem(@NotNull ItemResquestDTO itemResquestDTO) {

        if (itemRepository.existsBySku(itemResquestDTO.getSku())) {
            throw new IllegalArgumentException("Código de barra já cadastrado");
        }

        if (!itemResquestDTO.isActive()) {
            throw new IllegalArgumentException("Cadastro do item não pode ser criado DESATIVADO");
        }

        try {
            return new ItemReponseDTO(itemRepository.save(itemResquestDTO.itemEntity()));
        }
        catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Não foi possível cadastrar o item devido a uma violação de dados"+ e);
        }
    }

    // Buscar um ID
    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }

    // Retorna todos os registros pelo IDs
    public List<ItemReponseDTO> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemReponseDTO::new)
                .toList();
    }
}
