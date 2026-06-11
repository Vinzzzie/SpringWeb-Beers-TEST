package be.vdab.biershop.bestellingen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bestellingen")
class BestellingController {
    final BestellingService bestellingService;
    BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @PostMapping
    ResponseEntity<Integer> addBestelling(@RequestBody @Valid @NotNull BestellingDto bestelling) {
        return ResponseEntity.ok(bestellingService.addBestelling(bestelling));
    }
}
