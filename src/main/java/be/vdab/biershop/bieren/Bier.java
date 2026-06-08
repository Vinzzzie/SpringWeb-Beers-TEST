package be.vdab.biershop.bieren;

import java.math.BigDecimal;

public record Bier(
        int id,
        String naam,
        int brouwerId,
        BigDecimal alcohol,
        BigDecimal prijs,
        int besteld
) {
}
