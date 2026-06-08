package be.vdab.biershop.brouwers;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class BrouwerRepo {
    private final JdbcClient jdbc;
    BrouwerRepo(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    List<Brouwer>  findAll() {
        return jdbc.sql("""
            select id, naam, straat, huisNr, postcode, gemeente, omzet
            from brouwers
            order by naam;
            """).query(Brouwer.class).list();
    }
}
