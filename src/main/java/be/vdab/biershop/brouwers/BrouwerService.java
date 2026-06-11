package be.vdab.biershop.brouwers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class BrouwerService {
    final BrouwerRepo brouwerRepo;
    BrouwerService(BrouwerRepo brouwerRepo) {
        this.brouwerRepo = brouwerRepo;
    }

    List<BrouwerDto> findAll() {
        List<Brouwer> brouwers = brouwerRepo.findAll();
        if (brouwers.isEmpty()) throw new BrouwersNotFoundException("Geen brouwers gevonden");
        return brouwers.stream().map(BrouwerDto::fromBrouwer).toList();
    }
}
