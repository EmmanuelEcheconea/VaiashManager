package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.ClientFiltersRq;
import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    private ClientService clientService;
    @Autowired
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping("")
    public List<Client> retrieveAllClient() {
        List<Client> response = this.clientService.retrieveAllClient();
        return response;
    }

    @PostMapping("")
    public Client createClient(@RequestBody final Client client) {
        return this.clientService.createClient(client);
    }

    @PutMapping("{idClient}")
    public Client updateClient(@PathVariable("idClient") final Long idClient, @RequestBody final Client client) {
        return this.clientService.updateClient(idClient, client);
    }

    @DeleteMapping("{idClient}")
    public void deleteClient(@PathVariable("idClient") final Long idClient) {
        this.clientService.deleteClient(idClient);
    }

    @GetMapping("/clients")
    public Page<Client> listClients(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientService.getClients(pageable);
    }

    @PostMapping("/filters")
    public List<Client> filterClients(@RequestBody ClientFiltersRq clientFiltersRq) {
        return this.clientService.filters(clientFiltersRq);
    }

}
