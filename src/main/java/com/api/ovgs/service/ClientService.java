package com.api.ovgs.service;

import com.api.ovgs.dto.ClientResquestDTO;
import com.api.ovgs.dto.ClientResponseDTO;
import com.api.ovgs.entity.Client;
import com.api.ovgs.exception.FindByIdException;
import com.api.ovgs.exception.RegistrationFailedException;
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

   public ClientResponseDTO clientCreate(@NonNull ClientResquestDTO clientResquestDTO) {

       if (clientRepository.existsByEmail(clientResquestDTO.getEmail())) {
           throw new RegistrationFailedException("E-mail já cadastrado");
       }

       if (!clientResquestDTO.isActive()) {
           throw new IllegalArgumentException("Cadastro do cliente não pode ser criado DESATIVADO");
       }

       try {
           return new ClientResponseDTO(clientRepository.save(clientResquestDTO.toEntity()));
       }
       catch (DataIntegrityViolationException e) {
           throw new IllegalArgumentException("Não foi possível cadastrar o cliente devido a uma violação de dados");
       }
   }

   // Buscar por ID. Obs: Pode ou não retorna algo
   public ClientResponseDTO findById(Integer id) {

       Optional<Client> client = clientRepository.findById(id);
       if (client.isPresent()) {
           return new ClientResponseDTO(client.get());
       } else {
           throw new FindByIdException("Cliente não encontrado. ID = "+ id);
       }
   }

   // Retorna todos os registros pelo IDs
   public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
   }

    // Retorna clientes ativos ou inativos
    public List<ClientResponseDTO> findByActive(boolean active) {
        return clientRepository.findByActive(active)
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
    }

    // Atualizar dados de cliente
    public ClientResponseDTO update(Integer id, ClientResquestDTO clientResquestDTO) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new FindByIdException("Cliente não encontrado ID= "+id));

        client.setName(clientResquestDTO.getName());
        client.setEmail(clientResquestDTO.getEmail());
        client.setActive(clientResquestDTO.isActive());

        Client clientUpdate = clientRepository.save(client);
        return new ClientResponseDTO(clientUpdate);
    }
}