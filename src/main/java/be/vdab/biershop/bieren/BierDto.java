package be.vdab.biershop.bieren;

import java.math.BigDecimal;

record BierDto(
        int id,
        String naam,
        BigDecimal alcohol,
        BigDecimal prijs
) {
    public static BierDto fromBier(Bier bier) {
        return new BierDto(bier.id(), bier.naam(), bier.alcohol(), bier.prijs());
    }
}
