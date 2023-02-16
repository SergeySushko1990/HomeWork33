package ru.learnUp.learnupjava23.controller;

import org.springframework.web.bind.annotation.*;
import ru.learnUp.learnupjava23.dao.entity.BooksOrder;
import ru.learnUp.learnupjava23.dao.entity.OrderDetails;
import ru.learnUp.learnupjava23.dao.service.BookService;
import ru.learnUp.learnupjava23.dao.service.BooksOrderService;
import ru.learnUp.learnupjava23.dao.service.OrderDetailsService;
import ru.learnUp.learnupjava23.view.OrderDetailsFromView;
import ru.learnUp.learnupjava23.view.OrderDetailsToView;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("rest/order_details")
public class OrderDetailsControllerRest {

    private final OrderDetailsService detailsService;
    private final OrderDetailsToView mapper;
    private final OrderDetailsFromView mapperFrom;
    private final BookService bookService;
    private final BooksOrderService orderService;

    public OrderDetailsControllerRest(OrderDetailsService detailsService, OrderDetailsToView mapper,
                                      OrderDetailsFromView mapperFrom, BookService bookService,
                                      BooksOrderService orderService) {
        this.detailsService = detailsService;
        this.mapper = mapper;
        this.mapperFrom = mapperFrom;
        this.bookService = bookService;
        this.orderService = orderService;
    }

//    @GetMapping
//    public List<BooksOrderView> getOrders(
//            @RequestParam(value = "clientName", required = false) String clientName
//    ) {
//        return mapper.mapToViewList(orderService.getOrdersBy(new OrderFilter(clientName)));
//    }

    @GetMapping("/{order_detailsId}")
    public OrderDetailsToView getDetails(@PathVariable("order_detailsId") Long order_detailsId) {
        return mapper.mapToView(detailsService.getOrderDetailById(order_detailsId));
    }

    @PostMapping
    public OrderDetailsToView createOrderDetail(@RequestBody OrderDetailsFromView body) {
        if (body.getId() != null) {
            throw new EntityExistsException("Id must be null");
        }
        OrderDetails orderDetails = mapperFrom.mapFromView(body, orderService, bookService);
        OrderDetails createdOrderDetails = detailsService.createOrderDetails(orderDetails);
        BooksOrder bookOrder = orderService.getBooksOrderById(
                createdOrderDetails.getBooksOrder().getId()
        );
        bookOrder.setPurchaseAmount(bookOrder.getPurchaseAmount() + createdOrderDetails.getPriceOfBooks());
        orderService.update(bookOrder);
        return mapper.mapToView(createdOrderDetails);
    }


//    @PutMapping("/{orderId}")
//    public BooksOrderView updateOrder(
//            @PathVariable("orderId") Long orderId,
//            @RequestBody BooksOrderView body
//    ) {
//        if (body.getId() == null) {
//            throw new EntityNotFoundException("Try to found null entity");
//        }
//        if (!Objects.equals(orderId, body.getId())) {
//            throw new RuntimeException("Entity has bad id");
//        }
//
//        BooksOrder order = orderService.getBooksOrderById(orderId);
//
//        if (order.getOrderCost() != body.getOrderCost()) {
//            order.setOrderCost(body.getOrderCost());
//        }
//
//        BooksOrder updated = orderService.update(order);
//
//        return mapper.mapToView(updated);
//    }

    @DeleteMapping("/{order_detailsId}")
    public Boolean deleteOrderDetail(@PathVariable("order_detailsId") Long id) {
        return detailsService.delete(id);
    }
}
