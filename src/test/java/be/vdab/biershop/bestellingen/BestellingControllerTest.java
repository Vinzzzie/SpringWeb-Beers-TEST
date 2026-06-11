package be.vdab.biershop.bestellingen;

import be.vdab.biershop.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BestellingControllerTest extends AbstractControllerTest {

    @Mock
    BestellingRepo bestellingRepo;
    @Autowired @InjectMocks
    BestellingService bestellingService;

    BestellingControllerTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
    }

    @ParameterizedTest
    @ValueSource(strings = { "json/bestellingdto-ok.json" })
    void addBestelling_ok(String jsonLocation) throws IOException {
        var body = new ClassPathResource(jsonLocation).getContentAsString(StandardCharsets.UTF_8);
        var result = mvc.post().uri("/bestellingen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.OK);
        assertThat(result).hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result).bodyJson().extractingPath("$")
                .isNotEmpty().asNumber()
                .matches(number -> JdbcTestUtils.countRowsInTableWhere(jdbc, BESTELLINGEN, "id ="+number)==1);
    }

    @ParameterizedTest
    @EmptySource
    void addBestelling_badRequest_empty(String body) {
        var result = mvc.post().uri("/bestellingen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.BAD_REQUEST);
    }
}