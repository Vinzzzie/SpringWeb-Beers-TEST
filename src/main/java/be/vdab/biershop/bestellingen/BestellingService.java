package be.vdab.biershop.bestellingen;

import be.vdab.biershop.bestellingen.exceptions.BestellingException;
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
        int bestellingId = bestellingRepo.insertBestelling(dto);
        if (bestellingId==0)
            throw new BestellingException("Bestelling niet toegevoegd");
        var bestelRijen = bestellingRepo.insertBestelLijnen(bestellingId, dto.bierIds());
        if (bestelRijen.length==0 || bestelRijen.length!=dto.bierIds().size())
            throw new BestellingException("Bieren konden niet toegevoegd worden");
        var bestelMap = bierRepo.lockBesteldeBieren(dto.bierIds());
        if (bestelMap.isEmpty() || bestelMap.size()!=dto.bierIds().size())
            throw new BestellingException("Bieren konden niet opgehaald worden");
        var bierRijen = bierRepo.updateBesteldeBieren(bestelMap);
        if (bierRijen.length==0 || bierRijen.length!=dto.bierIds().size())
            throw new BestellingException("Bieren konden niet upgedated worden");
        return bestellingId;
    }

}
