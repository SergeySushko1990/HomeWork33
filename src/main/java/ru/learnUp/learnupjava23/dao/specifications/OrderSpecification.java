package ru.learnUp.learnupjava23.dao.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.learnUp.learnupjava23.dao.entity.BooksOrder;
import ru.learnUp.learnupjava23.dao.filters.OrderFilter;

import javax.persistence.criteria.Predicate;

public class OrderSpecification {

    public static Specification<BooksOrder> byFilter(OrderFilter filter) {

        return (root, q, cb) -> {

            Predicate predicate = cb.isNotNull(root.get("id"));

            if (filter.getClientName() != null) {
                predicate = cb.and(predicate, cb.like(root.get("client").get("fullName"), "%" + filter.getClientName() + "%"));
            }
            return predicate;
        };
    }
}
