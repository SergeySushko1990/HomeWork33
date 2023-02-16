package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.BooksOrder;
import ru.learnUp.learnupjava23.dao.entity.OrderDetails;
import ru.learnUp.learnupjava23.dao.service.ClientService;

import java.util.ArrayList;
import java.util.List;


@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class BooksOrderView {
    private Long id;

    private ClientView client;

    private int purchaseAmount;

    public BooksOrderView mapToView(BooksOrder booksOrder) {
        BooksOrderView view = new BooksOrderView();
        int cost = 0;
        view.setId(booksOrder.getId());
        view.setClient(new ClientView(booksOrder.getClient().getFullName(),
                booksOrder.getClient().getBirthDate()));
        view.setPurchaseAmount(booksOrder.getPurchaseAmount());
        if (booksOrder.getOrderDetails() != null) {
            for (OrderDetails orderDetails : booksOrder.getOrderDetails()) {
                cost += orderDetails.getPriceOfBooks();
            }
            view.setPurchaseAmount(cost);
        } else {
            view.setPurchaseAmount(booksOrder.getPurchaseAmount());
        }
        return view;
    }

    public List<BooksOrderView> mapToViewList(List<BooksOrder> booksOrders) {
        List<BooksOrderView> views = new ArrayList<>();
        booksOrders.forEach(booksOrder -> {
            int cost = 0;
            BooksOrderView view = new BooksOrderView();
            view.setId(booksOrder.getId());
            view.setClient(new ClientView(booksOrder.getClient().getFullName(),
                    booksOrder.getClient().getBirthDate()));
            if (booksOrder.getOrderDetails() != null) {
                for (OrderDetails orderDetails : booksOrder.getOrderDetails()) {
                    cost += orderDetails.getPriceOfBooks();
                }
                view.setPurchaseAmount(cost);
            } else {
                view.setPurchaseAmount(booksOrder.getPurchaseAmount());
            }
            views.add(view);
        });
        return views;
    }

    public BooksOrder mapFromView(BooksOrderView view, ClientService clientService) {
        BooksOrder booksOrder = new BooksOrder();
        booksOrder.setId(view.getId());
        booksOrder.setClient(clientService.getClientByName(view.getClient().getFullName()));
        booksOrder.setPurchaseAmount(view.getPurchaseAmount());
        return booksOrder;
    }
}
