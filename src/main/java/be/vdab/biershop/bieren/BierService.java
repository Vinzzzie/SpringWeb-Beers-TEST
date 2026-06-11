package be.vdab.biershop.bieren;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class BierService {
    final BierRepo bierRepo;
    BierService(BierRepo bierRepo) {
        this.bierRepo = bierRepo;
    }

    public int findAantalBieren() {
        return bierRepo.findTotaalAantal()
                .orElseThrow(() -> new BierNotFoundException("Geen bieren gevonden"));
    }

    List<BierDto> findBierenByBrouwerId(long brouwerID) {
        List<Bier> bieren = bierRepo.findBierenByBrouwerId(brouwerID);
        if (bieren.isEmpty()) throw new BierNotFoundException("BrouwerId heeft geen bieren");
        return bieren.stream().map(BierDto::fromBier).toList();
    }

}
