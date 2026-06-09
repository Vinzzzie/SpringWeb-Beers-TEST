package be.vdab.biershop.bestellingen;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
class BestellingRepo {
    JdbcClient client;
    JdbcTemplate template;
    BestellingRepo(JdbcClient client, JdbcTemplate template) {
        this.client = client;
        this.template = template;
    }

    int insertBestelling(BestellingDto bestelling) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        client.sql("""
            insert into bestellingen(naam, straat, huisnummer, postcode, gemeente)
            values (?, ?, ?, ?, ?);
            """).params(
                bestelling.naam(),
                bestelling.straat(),
                bestelling.huisnummer(),
                bestelling.postcode(),
                bestelling.gemeente()).update(keyHolder);
        return null==keyHolder.getKey() ? 0 : keyHolder.getKey().intValue();
    }

    int[] insertBestelLijnen(int bestellingId, List<Integer> bierIds) {
        List<Object[]> batchArgs = new ArrayList<>();
        bierIds.forEach(id -> batchArgs.add(new Object[] { bestellingId, id }));
        return template.batchUpdate("""
            insert into bestellijnen (bestelId, bierId)
            values (?, ?);
            """, batchArgs);
    }
}
