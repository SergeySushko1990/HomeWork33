package ru.learnUp.learnupjava23.dao.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.entity.Bookstore;

import java.util.List;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {

    List<Bookstore> findAll(Specification<Bookstore> specification);

    @Query(value = "select * from book_store bs " +
            "where bs.book_id = ?1 for update",
            nativeQuery = true)
    Bookstore getByBook(Book book);

}
