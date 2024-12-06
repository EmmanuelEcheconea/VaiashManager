package com.vaiashmanager.db.mapper;

import com.vaiashmanager.db.dto.request.ClientRqDTO;
import com.vaiashmanager.db.dto.response.ClientRsDTO;
import com.vaiashmanager.db.entity.Client;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientMapper {

    public ClientRsDTO clientToClientRsDTO(final Client client) {
        return ClientRsDTO.builder().id(client.getId()).nombre(client.getNombre()).build();
    }
    public Client clientRqDTOToClient(final ClientRqDTO clientRqDTO) {
        return Client.builder().nombre(clientRqDTO.getNombre()).build();
    }
}
