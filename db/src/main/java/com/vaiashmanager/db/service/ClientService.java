package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.ClientFiltersRq;
import com.vaiashmanager.db.dto.request.ClientRqDTO;
import com.vaiashmanager.db.dto.response.ClientRsDTO;
import com.vaiashmanager.db.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ClientService {
    public List<ClientRsDTO> retrieveAllClient();
    public ClientRsDTO createClient(final ClientRqDTO client);
    public ClientRsDTO updateClient(final Long idCliente, final ClientRqDTO client);
    public void deleteClient(final Long idCliente);

    public Page<ClientRsDTO> getClients(Pageable pageable);

    public List<ClientRsDTO> filters(ClientFiltersRq clientFiltersRq);
}
