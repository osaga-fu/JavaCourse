package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

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

@DisplayName("Actor Tests")
class ActorTest {
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

            @Test
            @DisplayName("Create actor with all fields")
            void createActorWithAllFields() {
                
                var actor = new Actor(1, "ANA", "SUAREZ");              

                assertNotNull(actor);
                assertAll("Actor",
                        () -> assertEquals(1, actor.getActorId(), "actorId"),
                        () -> assertEquals("ANA", actor.getFirstName(), "firstName"),
                        () -> assertEquals("SUAREZ", actor.getLastName(), "lastName"));
            }

            @ParameterizedTest(name = "{0} {1} {2}")
            @CsvSource(value = {"1,ANA,SUAREZ", "2,PEPI,FERNANDEZ", "3,MARCOS,FERNANDEZ"})
            @DisplayName("Create actor with only required fields parameterized")
            void createActorWithOnlyRequiredFields(int actorId, String firstName, String lastName) {
                var actor = new Actor(actorId, firstName, lastName);
                
                assertNotNull(actor);
                assertAll("Actor",
                        () -> assertEquals(actorId, actor.getActorId(), "actorId"),
                        () -> assertEquals(firstName, actor.getFirstName(), "firstName"),
                        () -> assertEquals(lastName, actor.getLastName(), "lastName"));
            }
        }

        @Nested
        @DisplayName("Unsuccessful Instanciation")
        class Unsuccessful {
        	 
        	 @ParameterizedTest(name = "{0} {1} {2}")
             @CsvSource(value = {"1,a,SUAREZ", "2,,PEREZ", "3,'',FERNANDEZ"})
             @DisplayName("Create actor with invalid name parameterized")
             void createActorWithInvalidName(int actorId, String firstName, String lastName) {
                 var actor = new Actor(actorId, firstName, lastName);
                 var violations = actor.getErrors();
                 assertFalse(violations.isEmpty(), "Expected validation violations but found none");
                 violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
             }
        	 
        	 @ParameterizedTest(name = "{0} {1} {2}")
             @CsvSource(value = {"1,MARI,", "2,LAURA,fernandez", "3,LUIS,' '"})
             @DisplayName("Create actor with invalid name parameterized")
             void createActorWithInvalidLastName(int actorId, String firstName, String lastName) {
                 var actor = new Actor(actorId, firstName, lastName);
                 var violations = actor.getErrors();
                 assertFalse(violations.isEmpty(), "Expected validation violations but found none");
                 violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
             }
        }
    }

    @Nested
    @DisplayName("Method Tests")
    class MethodTests {

    	  @Test
          @DisplayName("Test add film actor")
          void testAddFilmActor() {
              var actor = new Actor(1, "ANA", "SUAREZ");
              var filmActor = new FilmActor();
              var filmActors = new ArrayList<FilmActor>();
              actor.setFilmActors(filmActors);

              actor.addFilmActor(filmActor);
              assertAll("Add FilmActor",
                      () -> assertEquals(1, actor.getFilmActors().size(), "size"),
                      () -> assertTrue(actor.getFilmActors().contains(filmActor), "contains"),
                      () -> assertEquals(actor, filmActor.getActor(), "actor"));
          }

          @Test
          @DisplayName("Test remove film actor")
          void testRemoveFilmActor() {
              var actor = new Actor(1, "ELENA", "PEREZ");
              var filmActor = new FilmActor();
              var filmActors = new ArrayList<FilmActor>();
              filmActors.add(filmActor);
              actor.setFilmActors(filmActors);
              filmActor.setActor(actor);

              actor.removeFilmActor(filmActor);
              assertAll("Remove FilmActor",
                      () -> assertEquals(0, actor.getFilmActors().size(), "size"),
                      () -> assertFalse(actor.getFilmActors().contains(filmActor), "contains"),
                      () -> assertNull(filmActor.getActor(), "actor"));
          }
    }
}
