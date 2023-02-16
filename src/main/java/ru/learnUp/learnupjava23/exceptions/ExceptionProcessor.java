package ru.learnUp.learnupjava23.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionProcessor {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistException(
            EntityExistsException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex
    ) {
        List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                .map(Objects::toString)
                .collect(Collectors.toList());

        log.error("", ex);
        return new ResponseEntity<>(stackTrace, HttpStatus.BAD_REQUEST);
    }
}
