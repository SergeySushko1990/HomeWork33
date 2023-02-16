package ru.learnUp.learnupjava23.view;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PurchaseFromView {

    private List<BookViewForPurchase> books;

}