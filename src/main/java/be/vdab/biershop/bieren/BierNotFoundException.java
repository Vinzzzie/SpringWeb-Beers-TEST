package be.vdab.biershop.bieren;


public class BierNotFoundException extends RuntimeException {
    public BierNotFoundException(String message) {
        super(message);
    }
}
