package be.vdab.biershop.bestellingen;

import be.vdab.biershop.bieren.BierBestellingDto;
import be.vdab.biershop.bieren.BierRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class BestellingService {
    final BestellingRepo bestellingRepo;
    final BierRepo bierRepo;
    BestellingService(BestellingRepo bestellingRepo, BierRepo bierRepo) {
        this.bestellingRepo = bestellingRepo;
        this.bierRepo = bierRepo;
    }

    @Transactional
    public int addBestelling(BestellingDto dto) {
        int bestellingId = bestellingRepo.insertBestelling(dto.bestelling());
        var bestelRijen = bestellingRepo.insertBestelLijnen(bestellingId, dto.bierIds());
        var bestelMap = bierRepo.lockBesteldeBieren(dto.bierIds());
        var bierRijen = bierRepo.updateBesteldeBieren(bestelMap);
        return bestellingId;
    }

}
