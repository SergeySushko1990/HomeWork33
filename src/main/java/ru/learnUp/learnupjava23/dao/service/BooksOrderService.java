package ru.learnUp.learnupjava23.dao.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnUp.learnupjava23.dao.entity.Book;
import ru.learnUp.learnupjava23.dao.entity.BooksOrder;
import ru.learnUp.learnupjava23.dao.filters.OrderFilter;
import ru.learnUp.learnupjava23.dao.repository.BooksOrderRepository;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.learnUp.learnupjava23.dao.specifications.OrderSpecification.byFilter;

@Service
public class BooksOrderService {

    private final BooksOrderRepository booksOrderRepository;

    public BooksOrderService(BooksOrderRepository booksOrderRepository) {
        this.booksOrderRepository = booksOrderRepository;
    }

    public BooksOrder createBooksOrder(BooksOrder booksOrder) {
        return booksOrderRepository.save(booksOrder);
    }

    public List<BooksOrder> getBooksOrders() {
        return booksOrderRepository.findAll();
    }

    public BooksOrder getBooksOrderById(Long id) {
        return booksOrderRepository.getById(id);
    }

    public BooksOrder addBookToOrder(Book book, int count) {
        BooksOrder order = new BooksOrder();
        return booksOrderRepository.save(order);
    }

    public List<BooksOrder> getOrdersBy(OrderFilter filter) {
        Specification<BooksOrder> specification = where(byFilter(filter));
        return booksOrderRepository.findAll(specification);
    }

    @Transactional
    public BooksOrder update(BooksOrder order) {
        return booksOrderRepository.save(order);
    }

    public Boolean delete(Long id) {
        booksOrderRepository.delete(getBooksOrderById(id));
        return true;
    }
}
