package ru.learnUp.learnupjava23.dao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private BooksOrder booksOrder;

    @OneToOne
    @JoinColumn
    private Book book;

    @Column
    private int countOfBooks;

    @Column
    private int priceOfBooks;
}
