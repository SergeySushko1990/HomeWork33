package ru.learnUp.learnupjava23.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.learnUp.learnupjava23.dao.entity.Author;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.service.AuthorService;
import ru.learnUp.learnupjava23.dao.service.BookService;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/bookshop")
public class BookShopController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookShopController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // http://localhost:8080/bookshop/home
    @GetMapping
    public String home(Model model) {
        return "home";
    }

    // http://localhost:8080/bookshop/books
    @GetMapping("/books")
    public String books(Model model) {

        List<Book> books = bookService.getBooks();

        model.addAttribute(
                "books",
                books
        );
        return "books";
    }

    // http://localhost:8080/bookshop/authors
    @GetMapping("/authors")
    public String authors(Model model) {

        List<Author> authors = authorService.getAuthors();

        model.addAttribute(
                "authors",
                authors
        );
        return "authors";
    }

//    @GetMapping("/book")
//    public String createPage(Model model) {
//        model.addAttribute("book", new Book());
//        return "addBook";
//    }

//    @PostMapping("/addBook")
//    public String createBook(@ModelAttribute Book book, Model model) {
//        log.debug("{}", book);
//        model.addAttribute("book", book);
//        return "addBook";
//    }

}
