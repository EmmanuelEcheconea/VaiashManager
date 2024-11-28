package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.ClientFiltersRq;
import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.repository.ClientRepository;
import com.vaiashmanager.db.repository.ClientRepositoryPageable;
import com.vaiashmanager.db.service.ClientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private EntityManager entityManager;
    private ClientRepositoryPageable clientRepositoryPageable;
    @Autowired
    public ClientServiceImpl(final ClientRepository clientRepository,
                             ClientRepositoryPageable clientRepositoryPageable,
                             EntityManager entityManager) {
        this.clientRepository = clientRepository;
        this.clientRepositoryPageable = clientRepositoryPageable;
        this.entityManager = entityManager;
    }

    @Override
    public List<Client> retrieveAllClient() {
        Iterable<Client> clientIterable = this.clientRepository.findAll();
        List<Client> client = StreamSupport.stream(clientIterable.spliterator(), false)
                .collect(Collectors.toList());
        return client;
    }

    @Override
    public Client createClient(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long idClient, Client client) {
        final Client retrieveClient = this.clientRepository.findById(idClient).get();
        if(client.getNombre() != null) {
            retrieveClient.setNombre(client.getNombre());
        }
        final Client response = this.clientRepository.save(retrieveClient);
        return response;
    }

    @Override
    public void deleteClient(Long idClient) {
        final Client retrieveClient = this.clientRepository.findById(idClient).get();
        this.clientRepository.delete(retrieveClient);
    }

    @Override
    public Page<Client> getClients(Pageable pageable) {
        return this.clientRepositoryPageable.findAll(pageable);
    }

    @Override
    public List<Client> filters(ClientFiltersRq clientFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root = cq.from(Client.class);

        if (clientFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (clientFiltersRq.getId() > 0L) {
                predicates.add(cb.equal(root.get("id"), clientFiltersRq.getId()));
            }
            if (clientFiltersRq.getNombre() != null) {
                predicates.add(cb.equal(root.get("nombre"), clientFiltersRq.getNombre()));
            }

            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }
            return entityManager.createQuery(cq).getResultList();
        }
        return new ArrayList<>();
    }

}
