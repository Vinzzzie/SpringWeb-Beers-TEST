package be.vdab.biershop.bestellingen;

import be.vdab.biershop.AbstractControllerTest;
import be.vdab.biershop.bieren.BierRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BestellingControllerMockTest extends AbstractControllerTest {

    @MockitoBean
    BestellingRepo bestellingRepo;
    @MockitoBean
    BierRepo bierRepo;

    String bodyString;

    BestellingControllerMockTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
        BestellingService bestellingService = new BestellingService(bestellingRepo, bierRepo);
        BestellingController bestellingController = new BestellingController(bestellingService);
    }

    @BeforeAll
    void setUp() throws IOException {
        bodyString = new ClassPathResource("json/bestellingdto-ok.json" )
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void addBestelling_bestellingId_zero() {
        doReturn(0).when(bestellingRepo).insertBestelling(any());
        testExecutionForMessage(HttpStatus.CONFLICT, "Bestelling niet toegevoegd");
    }

    @Test
    void addBestelling_bestelRijen_empty() {
        doReturn(1).when(bestellingRepo).insertBestelling(any());
        doReturn(new int[]{}).when(bestellingRepo).insertBestelLijnen(anyInt(), anyList());
        testExecutionForMessage(HttpStatus.CONFLICT, "Bieren konden niet toegevoegd worden aan bestelling");

    }

    @Test
    void addBestelling_bestelRijen_badSize() {
        doReturn(1).when(bestellingRepo).insertBestelling(any());
        doReturn(new int[]{1,1,1,1}).when(bestellingRepo).insertBestelLijnen(anyInt(), anyList());
        testExecutionForMessage(HttpStatus.CONFLICT, "Bieren konden niet toegevoegd worden aan bestelling");
    }

    @Test
    void addBestelling_bestelMap_empty() {
        doReturn(1).when(bestellingRepo).insertBestelling(any());
        doReturn(new int[]{1,1,1}).when(bestellingRepo).insertBestelLijnen(anyInt(), anyList());
        doReturn(Map.of()).when(bierRepo).lockBesteldeBieren(anyList());
        testExecutionForMessage(HttpStatus.NOT_FOUND, "Bieren konden niet opgehaald worden");

    }

    @Test
    void addBestelling_bestelMap_wrongSize() {
        doReturn(1).when(bestellingRepo).insertBestelling(any());
        doReturn(new int[]{1,1,1}).when(bestellingRepo).insertBestelLijnen(anyInt(), anyList());
        doReturn(Map.of(1,1,2,1,3,1,4,1)).when(bierRepo).lockBesteldeBieren(anyList());
        testExecutionForMessage(HttpStatus.NOT_FOUND, "Bieren konden niet opgehaald worden");
    }

    @Test
    void addBestelling_bierRijen_empty() {
        doReturn(1).when(bestellingRepo).insertBestelling(any());
        doReturn(new int[]{1,1,1}).when(bestellingRepo).insertBestelLijnen(anyInt(), anyList());
        doReturn(Map.of(1,1,2,1,3,1)).when(bierRepo).lockBesteldeBieren(anyList());
        doReturn(new int[]{}).when(bierRepo).updateBesteldeBieren(anyMap());
        testExecutionForMessage(HttpStatus.CONFLICT, "Bieren konden niet upgedated worden");

    }

    @Test
    void addBestelling_bierRijen_wrongSize() {
        doReturn(1).when(bestellingRepo).insertBestelling(any());
        doReturn(new int[]{1,1,1}).when(bestellingRepo).insertBestelLijnen(anyInt(), anyList());
        doReturn(Map.of(1,1,2,1,3,1)).when(bierRepo).lockBesteldeBieren(anyList());
        doReturn(new int[]{1,1,1,1}).when(bierRepo).updateBesteldeBieren(anyMap());
        testExecutionForMessage(HttpStatus.CONFLICT, "Bieren konden niet upgedated worden");
    }

    private void testExecutionForMessage(HttpStatus status, String message) {
        var result = mvc.post().uri("/bestellingen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyString).exchange();
        assertThat(result).isNotNull().hasStatus(status)
                .hasBodyTextEqualTo(message);
    }
}