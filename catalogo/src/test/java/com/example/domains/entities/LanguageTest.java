package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class LanguageTest {

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
            
            @ParameterizedTest(name = "{0} {1} {2}")
            @CsvSource(value = {"1,Spanish", "2,English", "3,French"})
            @DisplayName("Create language with only required fields parameterized")
            void createLanguageWithOnlyRequiredFields(int languageId, String name) {
                var language = new Language(languageId, name);
                
                assertNotNull(language);
                assertAll("Category",
                        () -> assertEquals(languageId, language.getLanguageId() , "language Id"),
                        () -> assertEquals(name, language.getName(), "language name"));
            }

        }
        
        @Nested
        @DisplayName("Unsuccessful Instanciation")
        class Unsuccessful {
        	 
        	 @ParameterizedTest(name = "{0} {1} {2}")
             @CsvSource(value = {"1,", "2,' '", "3,Indonesio Balines Creole"})
             @DisplayName("Create language with invalid name parameterized")
             void createLanguageWithInvalidName(int languageId, String name) {
                 var language = new Language(languageId, name);
                 var violations = language.getErrors();
                 
                 assertFalse(violations.isEmpty(), "Expected validation violations but found none");
                 violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
             }
        	 
        }
    }
    

        @Nested
        @DisplayName("Method Tests")
        class MethodTests {

            @Test
            @DisplayName("Test addFilm")
            void testAddFilm() {
                var language = new Language(1, "English");
                var film = new Film();
                var films = new ArrayList<Film>();
                language.setFilms(films);

                language.addFilm(film);
                assertAll("Add Film",
                        () -> assertEquals(1, language.getFilms().size(), "size"),
                        () -> assertTrue(language.getFilms().contains(film), "contains"),
                        () -> assertEquals(language, film.getLanguage(), "language"));
            }


			@Test
            @DisplayName("Test removeFilm")
            void testRemoveFilm() {
                var language = new Language(2, "French");
                var film = new Film();
                var films = new ArrayList<Film>();
                films.add(film);
                language.setFilms(films);

                language.removeFilm(film);
                assertAll("Remove Film",
                        () -> assertEquals(0, language.getFilms().size(), "size"),
                        () -> assertFalse(language.getFilms().contains(film), "contains"),
                        () -> assertNull(film.getLanguage(), "language"));
            }

            @Test
            @DisplayName("Test addFilmsVO")
            void testAddFilmsVO() {
                var language = new Language(1, "English");
                var film = new Film();
                var filmsVO = new ArrayList<Film>();
                language.setFilmsVO(filmsVO);

                language.addFilmsVO(film);
                assertAll("Add FilmsVO",
                        () -> assertEquals(1, language.getFilmsVO().size(), "size"),
                        () -> assertTrue(language.getFilmsVO().contains(film), "contains"),
                        () -> assertEquals(language, film.getLanguageVO(), "languageVO"));
            }

            @Test
            @DisplayName("Test removeFilmsVO")
            void testRemoveFilmsVO() {
                var language = new Language(1, "English");
                var film = new Film();
                var filmsVO = new ArrayList<Film>();
                filmsVO.add(film);
                language.setFilmsVO(filmsVO);

                language.removeFilmsVO(film);
                assertAll("Remove FilmsVO",
                        () -> assertEquals(0, language.getFilmsVO().size(), "size"),
                        () -> assertFalse(language.getFilmsVO().contains(film), "contains"),
                        () -> assertNull(film.getLanguageVO(), "languageVO"));
            }
        }

}
