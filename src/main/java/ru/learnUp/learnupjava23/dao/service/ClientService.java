package ru.learnUp.learnupjava23.dao.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnUp.learnupjava23.dao.entity.Client;
import ru.learnUp.learnupjava23.dao.filters.ClientFilter;
import ru.learnUp.learnupjava23.dao.repository.ClientRepository;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.learnUp.learnupjava23.dao.specifications.ClientSpecification.byFilter;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.getById(id);
    }

    public List<Client> getClientBy(ClientFilter filter) {
        Specification<Client> specification = where(byFilter(filter));
        return clientRepository.findAll(specification);
    }

    public Client getClientByName(String name) {
        return clientRepository.getByName(name);
    }

    @Transactional
//    @CacheEvict(value = "client", key = "#client.id")
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    public Boolean delete(Long id) {
        clientRepository.delete(clientRepository.getById(id));
        return true;
    }
}
