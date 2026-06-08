package be.vdab.biershop.bieren;

import java.math.BigDecimal;

public record BierBestellingDto(
        int id,
        String naam,
        BigDecimal prijs
) {
    public static BierBestellingDto fromBier(Bier bier) {
        return new BierBestellingDto(bier.id(), bier.naam(), bier.prijs());
    }
}
