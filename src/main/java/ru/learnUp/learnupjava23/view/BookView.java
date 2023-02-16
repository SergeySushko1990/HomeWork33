package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.Author;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.entity.Bookstore;
import ru.learnUp.learnupjava23.dao.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class BookView {

//    private Long id;

    private String title;

    private int numberOfPages;

    private int yearOfPublication;

    private int price;

    private AuthorViewForBook author;

    private List<BookstoreViewForBook> bookstores;

    public BookView mapToView(Book book) {
        BookView view = new BookView();
//        view.setId(book.getId());
        view.setTitle(book.getTitle());
        view.setPrice(book.getPrice());
        view.setNumberOfPages(book.getNumberOfPages());
        view.setYearOfPublication(book.getYearOfPublication());
        view.setAuthor(new AuthorViewForBook(book.getAuthor().getFullName()));
        if (book.getBookstores() != null) {
            view.setBookstores(
                    book.getBookstores().stream()
                            .map(bookstore -> new BookstoreViewForBook(bookstore.getId(), bookstore.getAddress(), bookstore.getAmountOfBooks()))
                            .collect(Collectors.toList())
            );
        }
        return view;
    }

    public List<BookView> mapToViewList(List<Book> books) {
        List<BookView> views = new ArrayList<>();
        for (Book book : books) {
            BookView view = new BookView();
//            view.setId(book.getId());
            view.setTitle(book.getTitle());
            view.setPrice(book.getPrice());
            view.setNumberOfPages(book.getNumberOfPages());
            view.setYearOfPublication(book.getYearOfPublication());
            view.setAuthor(new AuthorViewForBook(book.getAuthor().getFullName()));
            if (book.getBookstores() != null) {
                view.setBookstores(
                        book.getBookstores().stream()
                                .map(bookstore -> new BookstoreViewForBook(bookstore.getId(), bookstore.getAddress(),bookstore.getAmountOfBooks()))
                                .collect(Collectors.toList())
                );
            }
            views.add(view);
        }
        return views;
    }

    public Book mapFromView(BookView view, BookService bookService) {
        Book book = new Book();
        List<Book> books = new ArrayList<>();
//        book.setId(view.getId());
        book.setTitle(view.getTitle());
        book.setPrice(view.getPrice());
        book.setNumberOfPages(view.getNumberOfPages());
        book.setYearOfPublication(view.getYearOfPublication());
        books.add(book);
        book.setAuthor(new Author(bookService.getBookByTitle(view.getTitle()).getAuthor().getId(), view.getAuthor().getFullName(), books));
        return book;
    }

}
