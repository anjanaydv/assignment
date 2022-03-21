package com.hilton.assignment.exception;

import com.hilton.assignment.model.FieldValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * This class will catch any errors/exception type which are mentioned using @ExceptionHandler. We can add more than
 * ConstraintViolationException and RuntimeException type. All InternalServerError is used as runtime exception for now.
 *
 * @author Anjana Yadav
 *
 */
@ControllerAdvice
@RestController
public class ExceptionController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<FieldValidationError>> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getConstraintViolations().stream().map(
                error -> new FieldValidationError(error.getPropertyPath().toString(), error.getMessage())
        ).collect(Collectors.toList()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<List<FieldValidationError>> runTimeException(RuntimeException ex) {
        List<FieldValidationError> errors = new ArrayList<>();
        errors.add(new FieldValidationError("error", ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}