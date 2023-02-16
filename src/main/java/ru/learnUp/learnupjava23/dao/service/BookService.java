package ru.learnUp.learnupjava23.dao.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.filters.BookFilter;
import ru.learnUp.learnupjava23.dao.repository.BookRepository;
import ru.learnUp.learnupjava23.exceptions.NameAlreadyExists;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.learnUp.learnupjava23.dao.specifications.BookSpecification.byFilter;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book createBook(Book book) {
        if (bookRepository.getBookByTitle(book.getTitle()) != null) {
            throw new NameAlreadyExists("This title already exists");
        }
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksBy(BookFilter filter) {
        Specification<Book> specification = where(byFilter(filter));
        return bookRepository.findAll(specification);
    }

    public Boolean delete(Long id) {
        bookRepository.delete(bookRepository.findBook1(id));
        return true;
    }

    public Book getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

//    @Cacheable(value = "Book")
    public Book getBookById(Long id) {
        return bookRepository.findBook1(id);
    }

//    @Cacheable(value = "Book")
    public List<Book> getBookByAuthor(String fullName) {
        return bookRepository.findByAuthor(fullName);
    }

    @Transactional
//    @CacheEvict(value = "book", key = "#book.id")
    public Book update(Book book) {
       return bookRepository.save(book);
    }
}
