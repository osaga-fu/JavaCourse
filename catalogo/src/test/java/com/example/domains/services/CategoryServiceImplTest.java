package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
public class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;
    
    @Nested
    @DisplayName("Valid Tests")
    class Valid {
    	@Test
        void testGetAll() {
            List<Category> categories = Arrays.asList(
                    new Category(1, "Category A"),
                    new Category(2, "Category B"),
                    new Category(3, "Category C")
            );
            when(categoryRepository.findAll()).thenReturn(categories);

            List<Category> result = categoryService.getAll();

            assertEquals(3, result.size());
            verify(categoryRepository, times(1)).findAll();
        }

        @Test
        void testGetOne() {
            Category category = new Category(1, "Category A");
            when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

            Optional<Category> result = categoryService.getOne(1);

            assertTrue(result.isPresent());
            assertEquals(1, result.get().getCategoryId());
            assertEquals("Category A", result.get().getName());
            verify(categoryRepository, times(1)).findById(1);
        }

        @Test
        void testAdd() throws DuplicateKeyException, InvalidDataException {
            Category category = new Category(4, "Category D");
            when(categoryRepository.save(category)).thenReturn(category);

            Category result = categoryService.add(category);

            assertNotNull(result);
            assertEquals(4, result.getCategoryId());
            assertEquals("Category D", result.getName());
            verify(categoryRepository, times(1)).save(category);
        }

        @Test
        void testModify() throws NotFoundException, InvalidDataException {
            Category category = new Category(1, "Category A");
            when(categoryRepository.existsById(1)).thenReturn(true);
            when(categoryRepository.save(category)).thenReturn(category);

            Category result = categoryService.modify(category);

            assertNotNull(result);
            assertEquals(1, result.getCategoryId());
            assertEquals("Category A", result.getName());
            verify(categoryRepository, times(1)).existsById(1);
            verify(categoryRepository, times(1)).save(category);
        }

        @Test
        void testDelete() throws InvalidDataException {
            Category category = new Category(1, "Category A");
            categoryService.delete(category);

            verify(categoryRepository, times(1)).delete(category);
        }

        @Test
        void testDeleteById() {
            categoryService.deleteById(1);

            verify(categoryRepository, times(1)).deleteById(1);
        }
        
        @Test
        void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
            when(categoryRepository.save(any(Category.class))).thenReturn(null, null);
            
            assertThrows(InvalidDataException.class, () -> categoryService.add(null));
            
            verify(categoryRepository, times(0)).save(null);
        }
    }

    
    @Nested
    @DisplayName("Invalid Tests")
    class Invalid {
 
    }
    
}
