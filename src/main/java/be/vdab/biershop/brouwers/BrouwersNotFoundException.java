package be.vdab.biershop.brouwers;

public class BrouwersNotFoundException extends RuntimeException {
    public BrouwersNotFoundException(String message) {
        super(message);
    }
}
