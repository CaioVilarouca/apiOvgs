package com.api.ovgs.controller;

import com.api.ovgs.dto.ClientResquestDTO;
import com.api.ovgs.dto.ClientResponseDTO;
import com.api.ovgs.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController { // Cliente

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    // Criar Cliente
    @PostMapping
    public ClientResponseDTO create(@RequestBody ClientResquestDTO clientResquestDTO) {
        return clientService.clientCreate(clientResquestDTO);
    }

    // Retorna todos os IDs [ATIVOS] e não [Ñ ATIVOS]
    @GetMapping
    public List<ClientResponseDTO> findAll(@RequestParam(required = false) Boolean active) {
        if (active == null) {
            return clientService.findAll();
        }
        return clientService.findByActive(active);
    }

    // Buscar por ID específico
    @GetMapping("/{id}")
    public ClientResponseDTO findById(@PathVariable Integer id) {
        return clientService.findById(id);
    }


    // Atualizar dados de cliente
    @PutMapping("/{id}")
    public ClientResponseDTO update(
            @PathVariable Integer id,
            @RequestBody ClientResquestDTO clientResquestDTO
    ) {
        return clientService.update(id, clientResquestDTO);
    }
}
