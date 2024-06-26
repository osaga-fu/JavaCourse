package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FilmTest {

    @Nested
    @DisplayName("Instanciation Tests")
    class InstanciationTests {

        @Nested
        @DisplayName("Successful Instanciation")
        class Successful {

            @Test
            @DisplayName("Create film with id and title")
            void createFilmWithIdAndTitle() {
                var film = new Film(1, "Inception");

                assertNotNull(film);
                assertAll("Film",
                        () -> assertEquals(1, film.getFilmId(), "id"),
                        () -> assertEquals("Inception", film.getTitle(), "title"));
            }

            @ParameterizedTest(name = "{0} {1}")
            @CsvSource(value = {"1,Inception", "2,Interstellar", "3,Tenet"})
            @DisplayName("Create film with id and title parameterized")
            void createFilmWithIdAndTitleParam(int id, String title) {
                var film = new Film(id, title);

                assertNotNull(film);
                assertAll("Film",
                        () -> assertEquals(id, film.getFilmId(), "id"),
                        () -> assertEquals(title, film.getTitle(), "title"));
            }

            @Test
            @DisplayName("Create full film object")
            void createFullFilm() {
                var language = new Language(1, "English");
                var rentalRate = new BigDecimal("2.99");
                var replacementCost = new BigDecimal("19.99");

                var film = new Film(1, "A great film", 120, "PG-13", (short) 2020, (byte) 5, rentalRate, replacementCost, "Inception", language, null);

                assertNotNull(film);
                assertAll("Film",
                        () -> assertEquals(1, film.getFilmId(), "id"),
                        () -> assertEquals("A great film", film.getDescription(), "description"),
                        () -> assertEquals(120, film.getLength(), "length"),
                        () -> assertEquals("PG-13", film.getRating(), "rating"),
                        () -> assertEquals((short) 2020, film.getReleaseYear(), "releaseYear"),
                        () -> assertEquals((byte) 5, film.getRentalDuration(), "rentalDuration"),
                        () -> assertEquals(rentalRate, film.getRentalRate(), "rentalRate"),
                        () -> assertEquals(replacementCost, film.getReplacementCost(), "replacementCost"),
                        () -> assertEquals("Inception", film.getTitle(), "title"),
                        () -> assertEquals(language, film.getLanguage(), "language"));
            }
        }

        @Nested
        @DisplayName("Unsuccessful Instanciation")
        class Unsuccessful {

            @ParameterizedTest(name = "{0} {1}")
            @CsvSource(value = {
                    "1,", 
                    "2,'   '",
                    "3,Una película increíblemente larga con un título interminable que sigue y sigue sin parar, alcanzando la impresionante longitud de 128 caracteres"
            })
            @DisplayName("Create film with invalid title parameterized")
            void createFilmWithInvalidTitle(int id, String title) {
                assertThrows(Exception.class, () -> new Film(id, title));
            }

            @ParameterizedTest(name = "{0} {1} {2} {3}")
            @CsvSource(value = {
                    "1,Inception,-10,2.99,19.99,1",
                    "2,Interstellar,0,2.99,19.99,1"
            })
            @DisplayName("Create film with invalid length parameterized")
            void createFilmWithInvalidLength(int id, String title, int length, BigDecimal rentalRate, BigDecimal replacementCost, int languageId) {
                assertThrows(Exception.class, () -> new Film(id, title, length, "PG-13", (short) 2020, (byte) 5, rentalRate, replacementCost, title, new Language(languageId), null));
            }

            @ParameterizedTest(name = "{0} {1} {2} {3}")
            @CsvSource(value = {
                    "1,Inception,2.99,-19.99,1",
                    "2,Interstellar,2.99,0,1"
            })
            @DisplayName("Create film with invalid replacement cost parameterized")
            void createFilmWithInvalidReplacementCost(int id, String title, BigDecimal rentalRate, BigDecimal replacementCost, int languageId) {
                assertThrows(Exception.class, () -> new Film(id, title, 120, "PG-13", (short) 2020, (byte) 5, rentalRate, replacementCost, title, new Language(languageId), null));
            }

            @ParameterizedTest(name = "{0} {1} {2} {3}")
            @CsvSource(value = {
                    "1,Inception,2.99,19.99,''",
                    "2,Interstellar,2.99,19.99,'   '"
            })
            @DisplayName("Create film with invalid language parameterized")
            void createFilmWithInvalidLanguage(int id, String title, BigDecimal rentalRate, BigDecimal replacementCost, String languageName) {
                var language = new Language(1, languageName);
                assertThrows(Exception.class, () -> new Film(id, title, 120, "PG-13", (short) 2020, (byte) 5, rentalRate, replacementCost, title, language, null));
            }
        
        }
    }
}
