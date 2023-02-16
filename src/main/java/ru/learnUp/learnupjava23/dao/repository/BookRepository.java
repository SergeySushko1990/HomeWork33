package ru.learnUp.learnupjava23.dao.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnUp.learnupjava23.dao.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitle(String title);

    List<Book> findAll(Specification<Book> specification);

    @Query(value = "select * from book b " +
            "where b.title = ?1",
            nativeQuery = true)
    Book findByTitle(String title);

    @Query(value = "select * from book b " +
            "left join author a on b.author_id = a.id  " +
            "where a.full_name = ?1",
            nativeQuery = true)
    List<Book> findByAuthor(String fullName);

    @Query(value = "select * from book b " +
            "where b.id = ?1",
            nativeQuery = true)
    Book findBook1(Long id);

    @Query(value = "select * from book b " +
            "where b.title = ?1",
            nativeQuery = true)
    Book getBookByTitle(String title);
}
