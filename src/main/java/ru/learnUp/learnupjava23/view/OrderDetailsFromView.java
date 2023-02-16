package ru.learnUp.learnupjava23.view;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.OrderDetails;
import ru.learnUp.learnupjava23.dao.service.BookService;
import ru.learnUp.learnupjava23.dao.service.BooksOrderService;

@Data
@Component
public class OrderDetailsFromView {

    private Long id;

    private BooksOrderView booksOrder;

    private BookViewForDetails book;

    private int countOfBooks;

    public OrderDetails mapFromView(OrderDetailsFromView view, BooksOrderService orderService,
                                    BookService bookservice) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBooksOrder(orderService.getBooksOrderById(view.getBooksOrder().getId()));
        orderDetails.setBook(bookservice.getBookByTitle(view.getBook().getTitle()));
        orderDetails.setCountOfBooks(view.getCountOfBooks());
        orderDetails.setPriceOfBooks(bookservice.getBookByTitle(view.getBook().getTitle())
                .getPrice() * view.getCountOfBooks());
        return orderDetails;
    }
}
