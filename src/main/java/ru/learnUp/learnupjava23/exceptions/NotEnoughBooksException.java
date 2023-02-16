package ru.learnUp.learnupjava23.exceptions;

public class NotEnoughBooksException extends RuntimeException{

    public NotEnoughBooksException(String str) {
        super(str);
    }
}