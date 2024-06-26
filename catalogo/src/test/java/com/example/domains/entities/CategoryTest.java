package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class CategoryTest {

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
            @CsvSource(value = {"1,Category1", "2,Category2", "3,Last Category"})
            @DisplayName("Create category with only required fields parameterized")
            void createCategoryWithOnlyRequiredFields(int categoryId, String name) {
                var category = new Category(categoryId, name);
                
                assertNotNull(category);
                assertAll("Category",
                        () -> assertEquals(categoryId, category.getCategoryId() , "category Id"),
                        () -> assertEquals(name, category.getName(), "category name"));
            }

        }
        
        @Nested
        @DisplayName("Unsuccessful Instanciation")
        class Unsuccessful {
        	 
        	 @ParameterizedTest(name = "{0} {1} {2}")
             @CsvSource(value = {"1,", "2,' '", "3,Caminando bajo la luna llena"})
             @DisplayName("Create category with invalid name parameterized")
             void createCategoryWithInvalidName(int categoryId, String name) {
                 var category = new Category(categoryId, name);
                 var violations = category.getErrors();
                 
                 assertFalse(violations.isEmpty(), "Expected validation violations but found none");
                 violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
             }
        	 
        }
    }
    
    @Nested
    @DisplayName("Method Tests")
    class MethodTests {

    	  @Test
          @DisplayName("Test add category")
          void testAddCategory() {
              var category = new Category(1, "Categoria1");
              var filmCategory = new FilmCategory();
              var filmCategories = new ArrayList<FilmCategory>();
              category.setFilmCategories(filmCategories);

              category.addFilmCategory(filmCategory);
              assertAll("Add FilmCAtegory",
                      () -> assertEquals(1, category.getFilmCategories().size(), "size"),
                      () -> assertTrue(category.getFilmCategories().contains(filmCategory), "contains"),
                      () -> assertEquals(category, filmCategory.getCategory(), "category"));
          }

          @Test
          @DisplayName("Test remove film category")
          void testRemoveFilmCategory() {
              var category = new Category(2, "Categoria2");
              var filmCategory = new FilmCategory();
              var filmCategories = new ArrayList<FilmCategory>();
              filmCategories.add(filmCategory);
              category.setFilmCategories(filmCategories);
              filmCategory.setCategory(category);

              category.removeFilmCategory(filmCategory);
              assertAll("Remove FilmCategory",
                      () -> assertEquals(0, category.getFilmCategories().size(), "size"),
                      () -> assertFalse(category.getFilmCategories().contains(filmCategory), "contains"),
                      () -> assertNull(filmCategory.getCategory(), "category"));
          }
    }
    

}
