package be.vdab.biershop;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.Default.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Enables non-static BeforeAll
abstract public class AbstractControllerTest {
    protected final String BESTELLIJNEN = "bestellijnen";
    protected final String BESTELLINGEN = "bestellingen";
    protected final String BIEREN = "bieren";
    protected final String BROUWERS = "brouwers";

    protected final MockMvcTester mvc;
    protected final JdbcClient jdbc;

    @Autowired
    public AbstractControllerTest(MockMvcTester mvc, JdbcClient jdbc) {
        this.mvc = mvc;
        this.jdbc = jdbc;
    }
}
