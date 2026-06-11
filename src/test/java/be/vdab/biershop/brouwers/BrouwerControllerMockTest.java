package be.vdab.biershop.brouwers;

import be.vdab.biershop.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class BrouwerControllerMockTest extends AbstractControllerTest {

    @MockitoBean
    BrouwerRepo brouwerRepo;

    public BrouwerControllerMockTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
        BrouwerService brouwerService = new BrouwerService(brouwerRepo);
        BrouwerController brouwerController = new BrouwerController(brouwerService);
    }

    @Test
    void getAllBrouwers() {
        doReturn(List.of()).when(brouwerRepo).findAll();
        var result = mvc.get().uri("/brouwers")
                .contentType(MediaType.APPLICATION_JSON).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.NOT_FOUND)
                .hasBodyTextEqualTo("Geen brouwers gevonden");
    }
}