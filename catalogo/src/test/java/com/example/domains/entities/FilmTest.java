package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Film Tests")
class FilmTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Instanciation Tests")
    class InstanciationTests {

        @Nested
        @DisplayName("Successful Instanciation")
        class Successful {

            @ParameterizedTest(name = "{0}, {1}, {2}, {3}, {4}, {5}")
            @CsvSource(value = {
                "1,Tenet,2,5,2.99,19.99",
                "2,Tootsie,3,7,3.99,24.99"
            })
            @DisplayName("Create film with only required fields parameterized")
            void createFilmWithOnlyRequiredFields(int filmId, String title, int languageId, byte rentalDuration,
                                                  BigDecimal rentalRate, BigDecimal replacementCost) {
                var language = new Language(languageId, "English");
                var film = new Film(filmId, title, language, rentalDuration, rentalRate, replacementCost);

                assertNotNull(film);
                assertAll("Film",
                        () -> assertEquals(filmId, film.getFilmId(), "filmId"),
                        () -> assertEquals(title, film.getTitle(), "title"),
                        () -> assertEquals(language, film.getLanguage(), "language"),
                        () -> assertEquals(rentalDuration, film.getRentalDuration(), "rentalDuration"),
                        () -> assertEquals(rentalRate, film.getRentalRate(), "rentalRate"),
                        () -> assertEquals(replacementCost, film.getReplacementCost(), "replacementCost"));
            }
        }

        @Nested
        @DisplayName("Unsuccessful Instanciation")
        class Unsuccessful {

            @ParameterizedTest(name = "{0}, {1}, {2}, {3}, {4}, {5}")
            @CsvSource(value = {
                "1,,2,5,2.99,19.99",
                "2,Invalid Language,,7,3.99,24.99",
                "3,Invalid Rate,3,5,-1.99,19.99",
                "4,Invalid Cost,3,7,3.99,-24.99"
            })
            @DisplayName("Create film with invalid parameters")
            void createFilmWithInvalidParameters(int filmId, String title, Integer languageId, Byte rentalDuration,
                                                 BigDecimal rentalRate, BigDecimal replacementCost) {
                Language language = languageId == null ? null : new Language(languageId, "English");
                var film = new Film(filmId, title, language, rentalDuration, rentalRate, replacementCost);
                var violations = validator.validate(film);

                assertFalse(violations.isEmpty(), "Expected validation violations but found none");
                violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
            }
        }
    }

    @Nested
    @DisplayName("Method Tests")
    class MethodTests {

        @Test
        @DisplayName("Test add actor")
        void testAddActor() {
            var film = new Film(1, "Sample Film", new Language(1, "English"), (byte) 5, new BigDecimal("2.99"), new BigDecimal("19.99"));
            var actor = new Actor(1, "John", "Doe");
            film.clearActors();

            film.addActor(actor);
            assertAll("Add Actor",
                    () -> assertEquals(1, film.getActors().size(), "size"),
                    () -> assertTrue(film.getActors().contains(actor), "contains"));
        }

        @Test
        @DisplayName("Test remove actor")
        void testRemoveActor() {
            var film = new Film(1, "Sample Film", new Language(1, "English"), (byte) 5, new BigDecimal("2.99"), new BigDecimal("19.99"));
            var actor = new Actor(1, "John", "Doe");
            film.clearActors();
            film.addActor(actor);

            film.removeActor(actor);
            assertAll("Remove Actor",
                    () -> assertEquals(0, film.getActors().size(), "size"),
                    () -> assertFalse(film.getActors().contains(actor), "contains"));
        }

        @Test
        @DisplayName("Test add category")
        void testAddCategory() {
            var film = new Film(1, "Sample Film", new Language(1, "English"), (byte) 5, new BigDecimal("2.99"), new BigDecimal("19.99"));
            var category = new Category(1, "Action");
            film.clearCategories();

            film.addCategory(category);
            assertAll("Add Category",
                    () -> assertEquals(1, film.getCategories().size(), "size"),
                    () -> assertTrue(film.getCategories().contains(category), "contains"));
        }

        @Test
        @DisplayName("Test remove category")
        void testRemoveCategory() {
            var film = new Film(1, "Sample Film", new Language(1, "English"), (byte) 5, new BigDecimal("2.99"), new BigDecimal("19.99"));
            var category = new Category(1, "Action");
            film.clearCategories();
            film.addCategory(category);

            film.removeCategory(category);
            assertAll("Remove Category",
                    () -> assertEquals(0, film.getCategories().size(), "size"),
                    () -> assertFalse(film.getCategories().contains(category), "contains"));
        }
    }
}
