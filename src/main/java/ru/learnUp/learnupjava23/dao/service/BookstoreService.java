package ru.learnUp.learnupjava23.dao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.entity.Bookstore;
import ru.learnUp.learnupjava23.dao.filters.BookstoreFilter;
import ru.learnUp.learnupjava23.dao.repository.BookstoreRepository;
import ru.learnUp.learnupjava23.exceptions.NotEnoughBooksException;

import javax.persistence.LockModeType;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.learnUp.learnupjava23.dao.specifications.BookstoreSpecification.byFilter;

@Slf4j
@Service
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    public BookstoreService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    public Bookstore createBookstore(Bookstore bookstore) {
        return bookstoreRepository.save(bookstore);
    }

    public List<Bookstore> getBookstores() {
        return bookstoreRepository.findAll();
    }

    public Bookstore getBookstoreById(Long id) {
        return bookstoreRepository.getById(id);
    }

    public Bookstore getStorageByBook(Book book) {
        return bookstoreRepository.getByBook(book);
    }

    public List<Bookstore> getBookstoreBy(BookstoreFilter filter) {
        Specification<Bookstore> specification = where(byFilter(filter));
        return bookstoreRepository.findAll(specification);
    }

    public Boolean deleteStore(Long id) {
        bookstoreRepository.delete(getBookstoreById(id));
        return true;
    }

    @Transactional
    @Lock(value = LockModeType.READ)
    public Bookstore update(Bookstore bookstore) {
        if (bookstore.getAmountOfBooks() >= 0) {
        bookstoreRepository.save(bookstore);
        } else {
            throw new NotEnoughBooksException("Not enough books");
        }
        return bookstore;
    }

    @Transactional
    @Lock(value = LockModeType.READ)
    public int buyBook(Book book, int amountOfBooks) {
        Bookstore bookstore = getStorageByBook(book);
        bookstore.setAmountOfBooks(bookstore.getAmountOfBooks() - amountOfBooks);
        update(bookstore);
        return bookstore.getAmountOfBooks();
    }

    @Transactional
    public int addBook(Book book, int amountOfBooks) {
        Bookstore bookstore = getStorageByBook(book);
        bookstore.setAmountOfBooks(bookstore.getAmountOfBooks() + amountOfBooks);
        update(bookstore);
        return bookstore.getAmountOfBooks();
    }
}
