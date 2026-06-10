package be.vdab.biershop.brouwers;

import be.vdab.biershop.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BrouwerControllerTest extends AbstractControllerTest {

    public BrouwerControllerTest(MockMvcTester mvc, JdbcClient jdbc) {
        super(mvc, jdbc);
    }

    @Test
    void getAllBrouwers() {
        var result = mvc.get().uri("/brouwers", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON).exchange();
        assertThat(result).isNotNull().hasStatus(HttpStatus.OK);
        assertThat(result).hasContentType(MediaType.APPLICATION_JSON);
    }
}