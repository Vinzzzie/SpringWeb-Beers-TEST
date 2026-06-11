package be.vdab.biershop.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BestellingException extends RuntimeException {
    BestellingException(String message) {
        super(message);
    }
}
