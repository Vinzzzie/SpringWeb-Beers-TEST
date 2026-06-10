package be.vdab.biershop.bieren;

import be.vdab.biershop.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BierControllerTest extends AbstractControllerTest {

    public BierControllerTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
    }

    @Test
    void getTotaalAantalBieren() {
        var result = mvc.get().uri("/bieren/aantal")
                .contentType(MediaType.APPLICATION_JSON).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.OK);
        assertThat(result).hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void getBierenVoorBrouwer() {
        var result = mvc.get().uri("/brouwers/{brouwerID}/bieren", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.OK);
        assertThat(result).hasContentType(MediaType.APPLICATION_JSON);
    }
}