package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.ClientFiltersRq;
import com.vaiashmanager.db.dto.request.ClientRqDTO;
import com.vaiashmanager.db.dto.response.ClientRsDTO;
import com.vaiashmanager.db.dto.response.ProductRsDTO;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.enums.CartState;
import com.vaiashmanager.db.enums.ClientError;
import com.vaiashmanager.db.enums.ProductError;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.mapper.ClientMapper;
import com.vaiashmanager.db.repository.CartRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private CartRepository cartRepository;
    private EntityManager entityManager;
    private ClientRepositoryPageable clientRepositoryPageable;
    private ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(final ClientRepository clientRepository,
                             ClientRepositoryPageable clientRepositoryPageable,
                             EntityManager entityManager,
                             CartRepository cartRepository,
                             ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientRepositoryPageable = clientRepositoryPageable;
        this.entityManager = entityManager;
        this.cartRepository = cartRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientRsDTO> retrieveAllClient() {
        Iterable<Client> clientIterable = this.clientRepository.findAll();
        if(!clientIterable.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return  StreamSupport.stream(clientIterable.spliterator(), false).map(this.clientMapper::clientToClientRsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientRsDTO createClient(ClientRqDTO client) {
        if(client!= null) {
            Client response = this.clientRepository.save(this.clientMapper.clientRqDTOToClient(client));
            System.out.println(response.getId());
            Cart cartSave = Cart.builder().client(response).
                    fechaCreacion(new Timestamp(System.currentTimeMillis()))
                    .state(CartState.ACTIVO.name()).build();
            this.cartRepository.save(cartSave);
            return this.clientMapper.clientToClientRsDTO(response);
        }
        throw new CustomExceptionHandler(ClientError.PRODUCT_EMPTY.getMessage(), ClientError.PRODUCT_EMPTY.getStatus());
    }

    @Override
    public ClientRsDTO updateClient(Long idClient, ClientRqDTO client) {
        final Optional<Client> retrieveClient = this.clientRepository.findById(idClient);
        if(retrieveClient.isEmpty()) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
        }
        if(client.getNombre() != null) {
            retrieveClient.get().setNombre(client.getNombre());
        }
        return this.clientMapper.clientToClientRsDTO(this.clientRepository.save(retrieveClient.get()));
    }

    @Override
    public void deleteClient(Long idClient) {
        final Optional<Client> retrieveClient = this.clientRepository.findById(idClient);
        if(retrieveClient.isEmpty()) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
        }
        this.clientRepository.delete(retrieveClient.get());
        Cart cart = this.cartRepository.findByClient_Id(retrieveClient.get());
        this.cartRepository.delete(cart);
    }

    @Override
    public Page<ClientRsDTO> getClients(Pageable pageable) {
        Page<Client> clientPage = clientRepositoryPageable.findAll(pageable);
        List<ClientRsDTO> clientRsDTOs =clientPage.getContent().stream().
                map(this.clientMapper::clientToClientRsDTO).collect(Collectors.toList());
        return new PageImpl<>(clientRsDTOs, pageable, clientPage.getTotalElements());
    }

    @Override
    public List<ClientRsDTO> filters(ClientFiltersRq clientFiltersRq) {
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
            List<Client> response = entityManager.createQuery(cq).getResultList();
            return response.stream().map(this.clientMapper::clientToClientRsDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
