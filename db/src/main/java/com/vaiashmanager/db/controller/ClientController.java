package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.request.ClientFiltersRq;
import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> retrieveAllClient() {
        List<Client> response = this.clientService.retrieveAllClient();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createClient(@RequestBody final Client client) {
        Client reponse = this.clientService.createClient(client);
        return ResponseEntity.ok(reponse);
    }

    @PutMapping("{idClient}")
    public ResponseEntity<?> updateClient(@PathVariable("idClient") final Long idClient, @RequestBody final Client client) {
        Client response = this.clientService.updateClient(idClient, client);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{idClient}")
    public void deleteClient(@PathVariable("idClient") final Long idClient) {
        this.clientService.deleteClient(idClient);
    }

    @GetMapping("/clients")
    public ResponseEntity<?> listClients(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> response = clientService.getClients(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filters")
    public ResponseEntity<?> filterClients(@RequestBody ClientFiltersRq clientFiltersRq) {
        List<Client> response = this.clientService.filters(clientFiltersRq);
        return ResponseEntity.ok(response);
    }

}
