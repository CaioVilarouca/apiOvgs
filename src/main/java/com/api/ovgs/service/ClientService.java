package com.api.ovgs.service;

import com.api.ovgs.dto.ClientResquestDTO;
import com.api.ovgs.dto.ClientResponseDTO;
import com.api.ovgs.entity.Client;
import com.api.ovgs.exception.FindByIdException;
import com.api.ovgs.exception.RegistrationFailedException;
import com.api.ovgs.exception.UserNotFoundException;
import com.api.ovgs.repository.ClientRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Criar um novo cliente
    public ClientResponseDTO clientCreate(@NonNull ClientResquestDTO clientResquestDTO) {

        if (clientRepository.existsByEmail(clientResquestDTO.getEmail())) {
            throw new RegistrationFailedException("E-mail já cadastrado");
        }

        boolean activeTrue = true;
        return new ClientResponseDTO(clientRepository.save(clientResquestDTO.toEntity(activeTrue)));
    }

    // Buscar por ID. Obs: Pode ou não retorna algo
    public ClientResponseDTO findById(Integer id) {

        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return new ClientResponseDTO(client.get());
        } else {
            throw new UserNotFoundException("Usuário não encontrado. ID = "+ id);
        }
    }

    // Retorna todos os registros pelo IDs
    public List<ClientResponseDTO> findAll() {

        return clientRepository.findAll()
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
    }

    // Retorna um cliente específico pelo ID [ATIVOS]
    public List<ClientResponseDTO> findByActive(boolean active) {

        return clientRepository.findByActive(active)
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
    }

    // Atualizar dados de cliente
    public ClientResponseDTO update(Integer id, ClientResquestDTO clientResquestDTO) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado. ID ="+ id));

        client.setName(clientResquestDTO.getName());
        client.setEmail(clientResquestDTO.getEmail());
        client.setActive(clientResquestDTO.getActive());

        Client clientUpdate = clientRepository.save(client);
        return new ClientResponseDTO(clientUpdate);
    }
}