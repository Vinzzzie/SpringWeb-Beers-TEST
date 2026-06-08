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
        return brouwerRepo.findAll().stream()
                .map(BrouwerDto::fromBrouwer).toList();
    }
}
