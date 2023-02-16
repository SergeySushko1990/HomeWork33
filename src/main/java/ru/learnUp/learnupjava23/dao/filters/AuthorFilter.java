package ru.learnUp.learnupjava23.dao.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorFilter {

    private final String fullName;
}
