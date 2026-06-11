package be.vdab.biershop.bieren;

import be.vdab.biershop.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BierControllerTest extends AbstractControllerTest {

    BierControllerTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
    }

    @Test
    void getTotaalAantalBieren() {
        var result = mvc.get().uri("/bieren/aantal")
                .contentType(MediaType.APPLICATION_JSON).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.OK);
        assertThat(result).hasContentType(MediaType.APPLICATION_JSON);
        int rows = JdbcTestUtils.countRowsInTable(jdbc, BIEREN);
        assertThat(result).bodyJson().extractingPath("$").asNumber().isEqualTo(rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4})
    void getBierenVoorBrouwer(int ints) {
        var result = mvc.get().uri("/brouwers/{brouwerID}/bieren", String.valueOf(ints))
                .contentType(MediaType.APPLICATION_JSON).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.OK);
        assertThat(result).hasContentType(MediaType.APPLICATION_JSON);
        int rows =JdbcTestUtils.countRowsInTableWhere(jdbc, BIEREN,  "brouwerId = "+ ints);
        assertThat(result).bodyJson().extractingPath("$").asArray().hasSize(rows);
    }
}