package be.vdab.biershop.bestellingen;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> addBestelling(@RequestBody @Valid BestellingDto bestelling) {
        int bestellingId = bestellingService.addBestelling(bestelling);
        return ResponseEntity.ok(bestellingId);
    }
}
