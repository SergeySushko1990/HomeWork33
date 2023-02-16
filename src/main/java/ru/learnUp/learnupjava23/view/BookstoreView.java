package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.Bookstore;
import ru.learnUp.learnupjava23.dao.service.BookService;
import ru.learnUp.learnupjava23.dao.service.BookstoreService;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class BookstoreView {

//    private Long id;

    private String address;

    private BookViewForBookstore book;

    private int amountOfBooks;

    public BookstoreView mapToView(Bookstore bookstore) {
        BookstoreView view = new BookstoreView();
        view.setAddress(bookstore.getAddress());
        view.setBook(new BookViewForBookstore(bookstore.getBook().getId(), bookstore.getBook().getTitle(),
                bookstore.getBook().getNumberOfPages(), bookstore.getBook().getYearOfPublication(),
                bookstore.getBook().getPrice(),
                new AuthorViewForBook(bookstore.getBook().getAuthor().getFullName())));
        view.setAmountOfBooks(bookstore.getAmountOfBooks());
        return view;
    }

    public List<BookstoreView> mapToViewList(List<Bookstore> bookstores) {
        List<BookstoreView> views = new ArrayList<>();
        for (Bookstore bookstore: bookstores) {
            BookstoreView view = new BookstoreView();
            view.setAddress(bookstore.getAddress());
            view.setBook(new BookViewForBookstore(bookstore.getBook().getId(), bookstore.getBook().getTitle(),
                    bookstore.getBook().getNumberOfPages(), bookstore.getBook().getYearOfPublication(),
                    bookstore.getBook().getPrice(),
                    new AuthorViewForBook(bookstore.getBook().getAuthor().getFullName())));
            view.setAmountOfBooks(bookstore.getAmountOfBooks());
            views.add(view);
        }
        return views;
    }

    public Bookstore mapFromView(BookstoreView view, BookService bookService, BookstoreService bookstoreService) {
        Bookstore bookstore = new Bookstore();
        bookstore.setId(bookstoreService.getStorageByBook(
                        bookService.getBookByTitle(
                                view.getBook().getTitle()))
                .getId());
        bookstore.setAddress(view.getAddress());
        bookstore.setBook(bookService.getBookByTitle(view.getBook().getTitle()));
        bookstore.setAmountOfBooks(view.getAmountOfBooks());
        return bookstore;
    }
}
