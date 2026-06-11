package be.vdab.biershop.bieren;

import be.vdab.biershop.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class BierControllerMockTest extends AbstractControllerTest {

    @MockitoBean
    BierRepo bierRepo;

    BierControllerMockTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
        BierService bierService = new BierService(bierRepo);
        BierController bierController = new BierController(bierService);
    }

    @Test
    void getTotaalAantalBieren() {
        doReturn(Optional.empty()).when(bierRepo).findTotaalAantal();
        var result = mvc.get().uri("/bieren/aantal").exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.NOT_FOUND)
                .hasBodyTextEqualTo("Geen bieren gevonden");

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4})
    void getBierenVoorBrouwer(int ints) {
        doReturn(List.of()).when(bierRepo).findBierenByBrouwerId(anyLong());
        var result = mvc.get().uri("/brouwers/{brouwerId}/bieren", String.valueOf(ints)).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.NOT_FOUND)
                .hasBodyTextEqualTo("BrouwerId heeft geen bieren");
    }
}