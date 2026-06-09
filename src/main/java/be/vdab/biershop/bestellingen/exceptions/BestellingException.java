package be.vdab.biershop.bestellingen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BestellingException extends RuntimeException {
    public BestellingException(String message) {
        super(message);
    }
}
