package com.api.ovgs.service;

import com.api.ovgs.dto.ItemReponseDTO;
import com.api.ovgs.dto.ItemResquestDTO;
import com.api.ovgs.entity.Client;
import com.api.ovgs.entity.Item;
import com.api.ovgs.exception.RegistrationFailedException;
import com.api.ovgs.exception.UserNotFoundException;
import com.api.ovgs.repository.ItemRepository;
import jakarta.validation.constraints.NotNull;
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
            throw new RegistrationFailedException("Código de barra já cadastrado");
        }

        return new ItemReponseDTO(itemRepository.save(itemResquestDTO.itemEntity(true)));
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

    // Retorna um cliente específico pelo ID
    public List<ItemReponseDTO> findByActive(Boolean active) {

        return itemRepository.findByActive(active)
                .stream()
                .map(ItemReponseDTO::new)
                .toList();
    }

    // Atualizar dados de cliente
    public ItemReponseDTO update(Integer id, ItemResquestDTO itemResquestDTO) {

        Item item = itemRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Item não encontrado. ID= "+ id));

        item.setPrice(itemResquestDTO.getPrice());
        item.setStock(item.getStock() + itemResquestDTO.getStock());
        item.setActive(itemResquestDTO.getActive());

        Item itemUpdate = itemRepository.save(item);
        return new ItemReponseDTO(itemUpdate);
    }
}
