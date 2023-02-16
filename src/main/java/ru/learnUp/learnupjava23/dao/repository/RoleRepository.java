package ru.learnUp.learnupjava23.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnUp.learnupjava23.dao.entity.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByRoleIn(Set<String> roles);

    @Query(value = "select * from roles r where r.role = ?1",
            nativeQuery = true)
    Role findByName(String role);

}
