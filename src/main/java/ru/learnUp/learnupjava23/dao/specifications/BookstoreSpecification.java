package ru.learnUp.learnupjava23.dao.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.learnUp.learnupjava23.dao.entity.Bookstore;
import ru.learnUp.learnupjava23.dao.filters.BookstoreFilter;

import javax.persistence.criteria.Predicate;

public class BookstoreSpecification {

    public static Specification<Bookstore> byFilter(BookstoreFilter filter) {

        return (root, q, cb) -> {

            Predicate predicate = cb.isNotNull(root.get("id"));

            if (filter.getBookTitle() != null) {
                predicate = cb.and(predicate, cb.like(root.get("book").get("title"), "%" + filter.getBookTitle() + "%"));
            }
            return predicate;
        };
    }
}
