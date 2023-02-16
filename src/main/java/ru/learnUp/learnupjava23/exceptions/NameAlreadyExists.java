package ru.learnUp.learnupjava23.exceptions;

public class NameAlreadyExists extends RuntimeException{
    public NameAlreadyExists(String str) {
        super(str);
    }
}
