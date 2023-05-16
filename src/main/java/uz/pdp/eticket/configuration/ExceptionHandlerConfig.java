package uz.pdp.eticket.configuration;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.eticket.domain.exceptions.*;
import org.postgresql.util.PSQLException;
import uz.pdp.eticket.domain.exceptions.IllegalAccessException;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<Object> handleApiRequestExceptions(DataNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateDataException.class)
    public ResponseEntity<Object> handleApiRequestExceptions(DuplicateDataException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
            PSQLException.class,
            EntityDestitutionException.class,
            MismatchedDataException.class,
            IllegalAccessException.class
    })
    public ResponseEntity<Object> handleApiRequestExceptions(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Object> handleApiRequestExceptions(NullPointerException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
