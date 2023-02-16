package ru.learnUp.learnupjava23.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.filters.BookFilter;
import ru.learnUp.learnupjava23.dao.service.BookService;
import ru.learnUp.learnupjava23.view.BookView;

import java.util.List;

@RestController
@RequestMapping("rest/bookshop")
public class WebFluxBookController {

    private final BookService bookService;
    private final BookView mapper;

    public WebFluxBookController(BookService bookService, BookView mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping
    public Flux<List<BookView>> getBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "yearOfPublication", required = false) String yearOfPublication,
            @RequestParam(value = "price", required = false) String price
    ) {
        return Flux.just(mapper.mapToViewList(bookService.getBooksBy(new BookFilter(title, yearOfPublication,price))));
    }

    @GetMapping("/{bookId}")
    public Mono<BookView> getBook(@PathVariable("bookId") Long bookId) {
        return Mono.just(mapper.mapToView(bookService.getBookById(bookId)));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public Mono<BookView> createBook(@RequestBody BookView body) {
        Book book = mapper.mapFromView(body, bookService);
        Book createdBook = bookService.createBook(book);
        return Mono.just(mapper.mapToView(createdBook));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{bookId}")
    public Mono<BookView> updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody BookView body
    ) {

        Book book = bookService.getBookById(bookId);

        if (!book.getTitle().equals(body.getTitle())) {
            book.setTitle(body.getTitle());
        }

        if (book.getPrice() != body.getPrice()) {
            book.setPrice(body.getPrice());
        }

        Book updated = bookService.update(book);

        return Mono.just(mapper.mapToView(updated));

    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{bookId}")
    public Mono<Boolean> deleteBook(@PathVariable("bookId") Long id) {
        return Mono.just(bookService.delete(id));
    }
}
