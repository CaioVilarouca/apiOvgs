package com.api.ovgs.controller;

import com.api.ovgs.dto.TypeTransportRequestDTO;
import com.api.ovgs.dto.TypeTransportResponseDTO;
import com.api.ovgs.entity.TypeTransport;
import com.api.ovgs.service.TypeTransportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/typeTransport")
public class TypeTransportController { // Tipo de transporte

    private final TypeTransportService typeTransportService;

    public TypeTransportController(TypeTransportService typeTransportService) {
        this.typeTransportService = typeTransportService;
    }

    // Criar transporte
    @PostMapping
    public TypeTransportResponseDTO create(@RequestBody TypeTransportRequestDTO typeTransportRequestDTO) {
        return  typeTransportService.createTypeTransport(typeTransportRequestDTO);
    }

    // Retorna todos os transporte
    @GetMapping
    public List<TypeTransportResponseDTO> findAll() {
        return typeTransportService.findAll();
    }

    // Retorna um ID específico
    @GetMapping("/{id}")
    public Optional<TypeTransport> findById(@PathVariable Integer id) {
        return typeTransportService.findById(id);
    }

    // Atualizar dados de transporte
    @PutMapping("/{id}")
    public TypeTransportResponseDTO update(
            @PathVariable Integer id,
            @RequestBody TypeTransportRequestDTO typeTransportRequestDTO
    ) {
        return typeTransportService.update(id, typeTransportRequestDTO);
    }
}
