package ru.learnUp.learnupjava23.dao.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.learnUp.learnupjava23.dao.entity.Client;
import ru.learnUp.learnupjava23.dao.filters.ClientFilter;

import javax.persistence.criteria.Predicate;

public class ClientSpecification {

    public static Specification<Client> byFilter(ClientFilter filter) {

        return (root, q, cb) -> {

            Predicate predicate = cb.isNotNull(root.get("id"));

            if (filter.getFullName() != null) {
                predicate = cb.and(predicate, cb.like(root.get("fullName"), "%" + filter.getFullName() + "%"));
            }
            return predicate;
        };
    }
}
