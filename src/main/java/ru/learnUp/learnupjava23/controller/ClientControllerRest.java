package ru.learnUp.learnupjava23.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.learnUp.learnupjava23.dao.entity.Client;
import ru.learnUp.learnupjava23.dao.entity.User;
import ru.learnUp.learnupjava23.dao.filters.ClientFilter;
import ru.learnUp.learnupjava23.dao.service.ClientService;
import ru.learnUp.learnupjava23.dao.service.UserService;
import ru.learnUp.learnupjava23.view.ClientView;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("rest/client")
public class ClientControllerRest {

    private final ClientService clientService;
    private final ClientView mapper;
    private final UserService userService;

    public ClientControllerRest(ClientService clientService, ClientView mapper, UserService userService) {
        this.clientService = clientService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public List<ClientView> getClients(
            @RequestParam(value = "fullName", required = false) String fullName
    ) {
        return mapper.mapToViewList(clientService.getClientBy(new ClientFilter(fullName)));
    }

    @Secured({"ROLE_USER"})
    @PreAuthorize("#user.name == authentication.principal.username")
    @GetMapping
    public ClientView getClient(Principal user) {
        User authUser = userService.loadUserByUsername(user.getName());
        return mapper.mapToView(clientService.getClientById(authUser.getClient().getId()));
    }

//    @PostMapping
//    public ClientView createClient(@RequestBody ClientView body) {
//        Client client = mapper.mapFromView(body);
//        Client createdClient = clientService.createClient(client);
//        return mapper.mapToView(createdClient);
//    }

    @Secured({"ROLE_USER"})
    @PreAuthorize("#user.name == authentication.principal.username")
    @PutMapping("/update")
    public ClientView updateClient(
            Principal user,
            @RequestBody ClientView body
    ) {

        User authUser = userService.loadUserByUsername(user.getName());

        Client client = authUser.getClient();

        if (!client.getFullName().equals(body.getFullName())) {
            client.setFullName(body.getFullName());
        }

        if (!(client.getBirthDate() == null)) {
            if (!client.getBirthDate().equals(body.getBirthDate()))
            {client.setBirthDate(body.getBirthDate());}
        }

        if (client.getBirthDate() == null) {
            client.setBirthDate(body.getBirthDate());
        }

        Client updated = clientService.update(client);

        return mapper.mapToView(updated);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{clientId}")
    public Boolean deleteBook(@PathVariable("clientId") Long id) {
        return clientService.delete(id);
    }
}
