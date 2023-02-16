package ru.learnUp.learnupjava23.controller;

import org.springframework.web.bind.annotation.*;
import ru.learnUp.learnupjava23.dao.entity.Bookstore;
import ru.learnUp.learnupjava23.dao.filters.BookstoreFilter;
import ru.learnUp.learnupjava23.dao.service.BookService;
import ru.learnUp.learnupjava23.dao.service.BookstoreService;
import ru.learnUp.learnupjava23.view.BookstoreView;

import java.util.List;


@RestController
@RequestMapping("rest/bookstore")
public class BookstoreControllerRest {

    private final BookstoreService bookstoreService;
    private final BookstoreView mapper;
    private final BookService bookService;

    public BookstoreControllerRest(BookstoreService bookstoreService, BookstoreView mapper, BookService bookService) {
        this.bookstoreService = bookstoreService;
        this.mapper = mapper;
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookstoreView> getBookstore(
            @RequestParam(value = "bookTitle", required = false) String bookTitle
    ) {
        return mapper.mapToViewList(bookstoreService.getBookstoreBy(new BookstoreFilter(bookTitle)));
    }

    @GetMapping("/{bookstoreId}")
    public BookstoreView getBookstoreById(@PathVariable("bookstoreId") Long bookstoreId) {
        return mapper.mapToView(bookstoreService.getBookstoreById(bookstoreId));
    }

    @PostMapping
    public BookstoreView createBookstore(@RequestBody BookstoreView body) {
        Bookstore store = mapper.mapFromView(body, bookService, bookstoreService);
        Bookstore createdBookstore = bookstoreService.createBookstore(store);
        return mapper.mapToView(createdBookstore);
    }

    @PutMapping("/{bookstoreId}")
    public BookstoreView updateBookstore(
            @PathVariable("bookstoreId") Long bookstoreId,
            @RequestBody BookstoreView body
    ) {

        Bookstore store = bookstoreService.getBookstoreById(bookstoreId);

        if (store.getAmountOfBooks() != body.getAmountOfBooks()) {
            store.setAmountOfBooks(body.getAmountOfBooks());
        }

        Bookstore updated = bookstoreService.update(store);

        return mapper.mapToView(updated);

    }

    @DeleteMapping("/{bookstoreId}")
    public Boolean deleteBookstore(@PathVariable("bookstoreId") Long id) {
        return bookstoreService.deleteStore(id);
    }
}
