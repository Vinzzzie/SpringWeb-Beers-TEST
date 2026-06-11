package be.vdab.biershop.bestellingen;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BestellingDtoTest {

    static final Logger log = LoggerFactory.getLogger(BestellingDtoTest.class);

    final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    final Validator validator = validatorFactory.getValidator();
    final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @ValueSource(strings = {
            "json/bestellingdto-bierids-unique.json",
            "json/bestellingdto-bierids-empty.json"
    })
    void bierIds(String jsonLocation) throws IOException {
        var jsonData = new ClassPathResource(jsonLocation).getContentAsString(StandardCharsets.UTF_8);
        var bestellingDto = objectMapper.readValue(jsonData, BestellingDto.class);
        Set<ConstraintViolation<BestellingDto>> violations = validator.validate(bestellingDto);
        log.info(violations.toString());
        assertThat(violations).isNotEmpty().hasSize(1)
                .allMatch(violation -> violation.getPropertyPath().toString().equals("bierIds"));
        assertThat(violations).allMatch(violation -> {
            var message = violation.getMessage();
            return switch (jsonLocation) {
                case "json/bestellingdto-bierids-unique.json" ->
                        message.equals("mag geen duplicaten bevatten");
                case "json/bestellingdto-bierids-empty.json" ->
                        message.equals("mag niet leeg zijn");
                default -> false;
            };
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    void naam(String naam) {
        var bestellingDto = new BestellingDto(List.of(11, 12, 13), naam, "SomeStraat", "666", 6666, "WhereAbout");
        Set<ConstraintViolation<BestellingDto>> violations = validator.validate(bestellingDto);
        log.info(violations.toString());
        assertThat(violations).isNotEmpty().hasSize(1)
                .allMatch(violation -> violation.getPropertyPath().toString().equals("naam"))
                .allMatch(violation -> violation.getMessage().equals("mag niet leeg zijn"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void straat(String straat) {
        var bestellingDto = new BestellingDto(List.of(11, 12, 13), "Guy Random", straat, "666", 6666, "WhereAbout");
        Set<ConstraintViolation<BestellingDto>> violations = validator.validate(bestellingDto);
        log.info(violations.toString());
        assertThat(violations).isNotEmpty().hasSize(1)
                .allMatch(violation -> violation.getPropertyPath().toString().equals("straat"))
                .allMatch(violation -> violation.getMessage().equals("mag niet leeg zijn"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void huisnummer(String huisnummer) {
        var bestellingDto = new BestellingDto(List.of(11, 12, 13), "Guy Random", "SomeStraat", huisnummer, 6666, "WhereAbout");
        Set<ConstraintViolation<BestellingDto>> violations = validator.validate(bestellingDto);
        log.info(violations.toString());
        assertThat(violations).isNotEmpty().hasSize(1)
                .allMatch(violation -> violation.getPropertyPath().toString().equals("huisnummer"))
                .allMatch(violation -> violation.getMessage().equals("mag niet leeg zijn"));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, 999, 10000})
    void postcode(int postcode) {
        var bestellingDto = new BestellingDto(List.of(11, 12, 13), "Guy Random", "SomeStraat", "666", postcode, "WhereAbout");
        Set<ConstraintViolation<BestellingDto>> violations = validator.validate(bestellingDto);
        log.info(violations.toString());
        assertThat(violations).isNotEmpty().hasSize(1)
                .allMatch(violation -> violation.getPropertyPath().toString().equals("postcode"));
        if (postcode < 1000) {
            assertThat(violations).allMatch(violation -> violation.getMessage().equals("moet groter of gelijk aan 1000 zijn"));
        } else {
            assertThat(violations).allMatch(violation -> violation.getMessage().equals("moet kleiner of gelijk aan 9999 zijn"));
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    void gemeente(String gemeente) {
        var bestellingDto = new BestellingDto(List.of(11, 12, 13), "Guy Random", "SomeStraat", "666", 6666, gemeente);
        Set<ConstraintViolation<BestellingDto>> violations = validator.validate(bestellingDto);
        log.info(violations.toString());
        assertThat(violations).isNotEmpty().hasSize(1)
                .allMatch(violation -> violation.getPropertyPath().toString().equals("gemeente"))
                .allMatch(violation -> violation.getMessage().equals("mag niet leeg zijn"));
    }
}