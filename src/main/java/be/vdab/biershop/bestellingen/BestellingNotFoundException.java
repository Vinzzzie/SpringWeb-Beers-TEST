package be.vdab.biershop.bestellingen;

public class BestellingNotFoundException extends RuntimeException {
    public BestellingNotFoundException(String message) {
        super(message);
    }
}
