package ru.learnUp.learnupjava23.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.learnUp.learnupjava23.dao.entity.BooksOrder;
import ru.learnUp.learnupjava23.dao.filters.OrderFilter;
import ru.learnUp.learnupjava23.dao.service.BooksOrderService;
import ru.learnUp.learnupjava23.dao.service.ClientService;
import ru.learnUp.learnupjava23.view.BooksOrderView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("rest/order")
public class OrderControllerRest {

    private final BooksOrderService orderService;
    private final BooksOrderView mapper;
    private final ClientService clientService;

    public OrderControllerRest(BooksOrderService orderService, BooksOrderView mapper, ClientService clientService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.clientService = clientService;
    }

//    @GetMapping
//    public List<BooksOrderView> getOrders(
//            @RequestParam(value = "clientName", required = false) String clientName
//    ) {
//        return mapper.mapToViewList(orderService.getOrdersBy(new OrderFilter(clientName)));
//    }

    @PreAuthorize("#user.name = authentication.principal.username")
    @GetMapping
    public List<BooksOrderView> getOrders(Principal user) {
        return mapper.mapToViewList(orderService.getOrdersBy(new OrderFilter(user.getName())));
    }

    @GetMapping("/{orderId}")
    public BooksOrderView getOrder(@PathVariable("orderId") Long orderId) {
        return mapper.mapToView(orderService.getBooksOrderById(orderId));
    }

    @PostMapping
    public BooksOrderView createOrder(@RequestBody BooksOrderView body) {
        if (body.getId() != null) {
            throw new EntityExistsException("Id must be null");
        }
        BooksOrder order = mapper.mapFromView(body, clientService);
        BooksOrder createdOrder = orderService.createBooksOrder(order);
        return mapper.mapToView(createdOrder);
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/{orderId}")
    public BooksOrderView updateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody BooksOrderView body
    ) {
        if (body.getId() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(orderId, body.getId())) {
            throw new RuntimeException("Entity has bad id");
        }

        BooksOrder order = orderService.getBooksOrderById(orderId);

        if (order.getPurchaseAmount() != body.getPurchaseAmount()) {
            order.setPurchaseAmount(body.getPurchaseAmount());
        }

        BooksOrder updated = orderService.update(order);

        return mapper.mapToView(updated);
    }

    @DeleteMapping("/{orderId}")
    public Boolean deleteOrder(@PathVariable("orderId") Long id) {
        return orderService.delete(id);
    }
}


