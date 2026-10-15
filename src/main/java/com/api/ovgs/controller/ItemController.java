package com.api.ovgs.controller;

import com.api.ovgs.dto.ItemReponseDTO;
import com.api.ovgs.dto.ItemResquestDTO;
import com.api.ovgs.entity.Item;
import com.api.ovgs.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Criar item
    @PostMapping
    public ItemReponseDTO create(@RequestBody ItemResquestDTO itemResquestDTO) {
        return itemService.createItem(itemResquestDTO);
    }

    // Retorna todos os IDs
    @GetMapping
    public ItemReponseDTO findAll() {
        return itemService.findAll();
    }

    // Retorna um ID específico
    @GetMapping("/{id}")
    public Optional<Item> findById(@PathVariable Integer id) {
        return itemService.findById(id);
    }
}