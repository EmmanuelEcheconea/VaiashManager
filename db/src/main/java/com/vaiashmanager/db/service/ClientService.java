package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.ClientFiltersRq;
import com.vaiashmanager.db.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ClientService {
    public List<Client> retrieveAllClient();
    public Client createClient(final Client client);
    public Client updateClient(final Long idCliente, final Client client);
    public void deleteClient(final Long idCliente);

    public Page<Client> getClients(Pageable pageable);

    public List<Client> filters(ClientFiltersRq clientFiltersRq);
}
