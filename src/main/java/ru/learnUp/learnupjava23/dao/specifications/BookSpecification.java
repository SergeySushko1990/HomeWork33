package ru.learnUp.learnupjava23.dao.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.filters.BookFilter;

import javax.persistence.criteria.Predicate;

public class BookSpecification{

    public static Specification<Book> byFilter(BookFilter filter) {

        return (root, q, cb) -> {

            Predicate predicate = cb.isNotNull(root.get("id"));

            if (filter.getTitle() != null) {
                predicate = cb.and(predicate, cb.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            if (filter.getYearOfPublication() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("yearOfPublication"), filter.getYearOfPublication()));
            }
            if (filter.getPrice() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("price"), filter.getPrice()));
            }

            return predicate;
        };
    }
}
