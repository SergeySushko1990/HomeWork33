package ru.learnUp.learnupjava23.dao.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnUp.learnupjava23.dao.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll(Specification<Client> specification);

    @Query(value = "select * from client c where c.full_name = ?1",
            nativeQuery = true)
    Client getByName(String name);
}
