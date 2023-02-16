package ru.learnUp.learnupjava23.dao.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "book_store")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Bookstore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn
    private Book book;

    @Column
    private int amountOfBooks;

    public Bookstore(Book book, int amountOfBooks) {
        this.book = book;
        this.amountOfBooks = amountOfBooks;
    }
}
