package ru.learnUp.learnupjava23.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.learnUp.learnupjava23.dao.entity.*;
import ru.learnUp.learnupjava23.dao.service.*;
import ru.learnUp.learnupjava23.view.BookViewForPurchase;
import ru.learnUp.learnupjava23.view.PurchaseFromView;

import java.util.Calendar;

@RestController
@RequestMapping("/purchase")
public class PurchaseControllerRest {

    private final BooksOrderService orderService;
    private final OrderDetailsService detailsService;
    private final BookService bookService;
    private final BookstoreService storageService;
    private final UserService userService;
    private final OrderHistoryService historyService;

    public PurchaseControllerRest(BooksOrderService orderService,
                                  OrderDetailsService detailsService,
                                  BookService bookService, BookstoreService storageService, UserService userService, OrderHistoryService historyService) {
        this.orderService = orderService;
        this.detailsService = detailsService;
        this.bookService = bookService;
        this.storageService = storageService;
        this.userService = userService;
        this.historyService = historyService;
    }

    @PostMapping
    public String createPurchase(@RequestBody PurchaseFromView purchaseView) {

        for (BookViewForPurchase bookFromView : purchaseView.getBooks()) {
            if (storageService.getStorageByBook(
                            bookService.
                                    getBookByTitle(bookFromView.getBookTitle()))
                    .getAmountOfBooks() < bookFromView.getCountOfBooks()) {
                return "Not enough books: \"" + bookFromView.getBookTitle() + "\"\n"
                        + "We have just "
                        + storageService.getStorageByBook(bookService.
                                getBookByTitle(bookFromView.getBookTitle()))
                        .getAmountOfBooks()
                        + " book/s...";
            }
        }

        StringBuilder result = new StringBuilder();
        BooksOrder order = new BooksOrder();
        OrderHistory orderHistory = new OrderHistory();
        User user = userService.loadUserByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        order.setClient(user.getClient());
        BooksOrder createdOrder = orderService.createBooksOrder(order);

        for (BookViewForPurchase bookFromView : purchaseView.getBooks()) {
            OrderDetails detail = new OrderDetails();
            Book book = bookService.getBookByTitle(bookFromView.getBookTitle());
            storageService.buyBook(book, bookFromView.getCountOfBooks());
            result.append("You bought \"").append(bookFromView.getBookTitle()).append("\" successfully.\n");
            detail.setBook(book);
            detail.setBooksOrder(createdOrder);
            detail.setCountOfBooks(bookFromView.getCountOfBooks());
            detail.setPriceOfBooks(book.getPrice() * detail.getCountOfBooks());
            detailsService.createOrderDetails(detail);
            createdOrder.setPurchaseAmount(createdOrder.getPurchaseAmount() + detail.getPriceOfBooks());
        }
        orderService.update(createdOrder);

        orderHistory.setClient(user.getClient());
        orderHistory.setOrder(createdOrder);
        orderHistory.setCal(Calendar.getInstance());
        historyService.create(orderHistory);

        return result + "Thank you!";
    }
}
