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
        return bierRepo.findTotaalAantal().orElse(0);
    }

    List<BierDto> findBierenByBrouwerId(long brouwerID) {
        return bierRepo.findBierenByBrouwerId(brouwerID)
                .stream().map(BierDto::fromBier).toList();
    }

    public BierDto findBierById(int id) {
        return bierRepo.findDetails(id)
                .map(BierDto::fromBier)
                .orElseThrow(() -> new RuntimeException("Niet gevonden"));

    }
}
