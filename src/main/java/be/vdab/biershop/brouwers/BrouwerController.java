package be.vdab.biershop.brouwers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brouwers")
class BrouwerController {
    final BrouwerService brouwerService;
    BrouwerController(BrouwerService brouwerService) {
        this.brouwerService = brouwerService;
    }

    @GetMapping()
    ResponseEntity<List<BrouwerDto>> getAllBrouwers() {
        List<BrouwerDto> brouwers = brouwerService.findAll();
        return ResponseEntity.ok(brouwers);
    }

}
