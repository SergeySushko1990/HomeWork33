package ru.learnUp.learnupjava23.view;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.OrderDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class OrderDetailsToView {

    private BookViewForDetails book;

    private int countOfBooks;

    private int priceOfBooks;

    public OrderDetailsToView mapToView(OrderDetails orderDetails) {
        OrderDetailsToView view = new OrderDetailsToView();
        view.setBook(new BookViewForDetails(orderDetails.getBook().getTitle()));
        view.setCountOfBooks(orderDetails.getCountOfBooks());
        view.setPriceOfBooks(orderDetails.getPriceOfBooks());
        return view;
    }

    public List<OrderDetailsToView> mapToViewList(List<OrderDetails> orderDetailsList) {
        List<OrderDetailsToView> views = new ArrayList<>();
        orderDetailsList.forEach(orderDetails -> {
            OrderDetailsToView view = new OrderDetailsToView();
            view.setBook(new BookViewForDetails(orderDetails.getBook().getTitle()));
            view.setCountOfBooks(orderDetails.getCountOfBooks());
            view.setPriceOfBooks(orderDetails.getPriceOfBooks());
        });
        return views;
    }

}
