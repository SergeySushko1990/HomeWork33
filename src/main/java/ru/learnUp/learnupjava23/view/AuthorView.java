package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuthorView {

    private String fullName;

    private List<BookViewForAuthor> books;

    public AuthorView mapToView(Author author) {
        AuthorView view = new AuthorView();
        view.setFullName(author.getFullName());
        if (author.getBooks() != null) {
            view.setBooks(
                    author.getBooks().stream()
                            .map(book -> new BookViewForAuthor(book.getId(), book.getTitle(), book.getNumberOfPages(),
                                    book.getYearOfPublication(), book.getPrice()))
                            .collect(Collectors.toList())
            );
        }
        return view;
    }

    public List<AuthorView> mapToViewList(List<Author> authors) {
        List<AuthorView> views = new ArrayList<>();
        for (Author author : authors) {
            AuthorView view = new AuthorView();
            view.setFullName(author.getFullName());
            if (author.getBooks() != null) {
                view.setBooks(
                        author.getBooks().stream()
                                .map(book -> new BookViewForAuthor(book.getId(), book.getTitle(), book.getNumberOfPages(),
                                        book.getYearOfPublication(), book.getPrice()))
                                .collect(Collectors.toList())
                );
            }
            views.add(view);
        }
        return views;
    }

    public Author mapFromView(AuthorView view) {
        Author author = new Author();
        author.setFullName(view.getFullName());
        return author;
    }
}
