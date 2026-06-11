package be.vdab.biershop.bieren;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BierRepo {
    final JdbcClient client;
    final NamedParameterJdbcTemplate template;
    BierRepo(JdbcClient client, NamedParameterJdbcTemplate template) {
        this.client = client;
        this.template = template;
    }

    Optional<Integer> findTotaalAantal() {
        return client.sql("""
            select count(*) from bieren;
        """).query(Integer.class).optional();
    }

    List<Bier> findBierenByBrouwerId(long brouwerID) {
        return client.sql("""
            select id, naam, brouwerId, alcohol, prijs, besteld
            from bieren where brouwerId = (?);
        """).params(brouwerID).query(Bier.class).list();
    }

    public Map<Integer, Integer> lockBesteldeBieren(List<Integer> bierIds) {
        MapSqlParameterSource params = new MapSqlParameterSource("ids", bierIds);
        return template.query("""
            select id, besteld from bieren
            where id in (:ids) for update;
        """,params, rs -> {
            Map<Integer, Integer> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getInt("id"), rs.getInt("besteld"));
            }
            return result;
        });
    }

    public int[] updateBesteldeBieren(Map<Integer, Integer> bestelMap) {
        var sql = """
            update bieren set besteld = (:besteld)
            where id = (:id);
        """;
        MapSqlParameterSource[] params = bestelMap.entrySet().parallelStream()
                .map(entry -> {
                    MapSqlParameterSource param = new MapSqlParameterSource();
                    param.addValue("id", entry.getKey());
                    param.addValue("besteld", entry.getValue());
                    return param;
                }).toArray(MapSqlParameterSource[]::new);
        return template.batchUpdate(sql, params);

    }
}
