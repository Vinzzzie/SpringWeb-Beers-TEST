package be.vdab.biershop;

import be.vdab.biershop.bestellingen.BestellingException;
import be.vdab.biershop.bestellingen.BestellingNotFoundException;
import be.vdab.biershop.bieren.BierException;
import be.vdab.biershop.bieren.BierNotFoundException;
import be.vdab.biershop.brouwers.BrouwersNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = BiershopControllerAdvice.class)
class BiershopControllerAdvice {

    @ExceptionHandler(BestellingException.class)
    ResponseEntity<?> handleBestellingException(BestellingException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(BestellingNotFoundException.class)
    ResponseEntity<?> handleBestellingNotFoundException(BestellingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BierException.class)
    ResponseEntity<?> handleBestellingException(BierException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(BierNotFoundException.class)
    ResponseEntity<?> handleBestellingNotFoundException(BierNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BrouwersNotFoundException.class)
    ResponseEntity<?> handleBrouwersNotFoundException(BrouwersNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }



}
