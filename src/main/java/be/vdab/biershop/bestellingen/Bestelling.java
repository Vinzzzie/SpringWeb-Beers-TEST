package be.vdab.biershop.bestellingen;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

record Bestelling(
        int id,
        @NotEmpty String naam,
        @NotEmpty String straat,
        @NotEmpty String huisnummer,
        @Min(1000) @Max(9999) int postcode,
        @NotEmpty String gemeente
) {
}
