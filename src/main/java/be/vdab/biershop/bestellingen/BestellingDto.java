package be.vdab.biershop.bestellingen;

import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

record BestellingDto(
        @UniqueElements List<Integer> bierIds,
        @Valid Bestelling bestelling
) {
}
