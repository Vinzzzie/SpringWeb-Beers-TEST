package be.vdab.biershop.bestellingen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

record BestellingDto(
        @UniqueElements @NotEmpty List<Integer> bierIds,
        @NotEmpty String naam,
        @NotEmpty String straat,
        @NotEmpty String huisnummer,
        @Min(1000) @Max(9999) int postcode,
        @NotEmpty String gemeente
) {
}
