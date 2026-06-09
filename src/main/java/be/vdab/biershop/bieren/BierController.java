package be.vdab.biershop.bieren;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class BierController {
    final BierService bierService;
    BierController(BierService bierService) {
        this.bierService = bierService;
    }

    @GetMapping("/bieren/aantal")
    ResponseEntity<Integer> getTotaalAantalBieren() {
        return ResponseEntity.ok(bierService.findAantalBieren());
    }

    @GetMapping("/brouwers/{brouwerID}/bieren")
    ResponseEntity<List<BierDto>> getBierenVoorBrouwer(@PathVariable long brouwerID) {
        return ResponseEntity.ok(bierService.findBierenByBrouwerId(brouwerID));
    }

}
