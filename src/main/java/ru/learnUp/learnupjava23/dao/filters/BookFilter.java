package ru.learnUp.learnupjava23.dao.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookFilter {

    private final String title;

    private final String yearOfPublication;

    private final String price;
}
