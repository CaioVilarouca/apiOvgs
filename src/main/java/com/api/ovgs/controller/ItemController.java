package com.api.ovgs.controller;

import com.api.ovgs.dto.ClientResponseDTO;
import com.api.ovgs.dto.ItemReponseDTO;
import com.api.ovgs.dto.ItemResquestDTO;
import com.api.ovgs.entity.Item;
import com.api.ovgs.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ItemReponseDTO create(@Valid @RequestBody ItemResquestDTO itemResquestDTO) {
        return itemService.createItem(itemResquestDTO);
    }

    // Retorna todos os IDs [ATIVOS] e não [Ñ ATIVOS] ex: /item?active=true ou false
    @GetMapping
    public List<ItemReponseDTO> findAll(@RequestParam(required = false) Boolean active) {
        if (active == null) {
            return itemService.findAll();
        }
        return itemService.findByActive(active);
    }

    // Retorna um ID específico
    @GetMapping("/{id}")
    public Optional<Item> findById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    // Atualizar dados de itens
    @PutMapping("/{id}")
    public ItemReponseDTO update(
            @PathVariable Integer id,
            @Valid @RequestBody ItemResquestDTO itemResquestDTO) {

        return itemService.update(id, itemResquestDTO);
    }
}