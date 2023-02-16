package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class BookViewForBookstore {

    private Long id;

    private String title;

    private int numberOfPages;

    private int yearOfPublication;

    private int price;

    private AuthorViewForBook author;
}
