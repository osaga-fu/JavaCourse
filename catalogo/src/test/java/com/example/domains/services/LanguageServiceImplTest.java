package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
class LanguageServiceImplTest {

	 @MockBean
	    private LanguageRepository languageRepository;

	    @Autowired
	    private LanguageService languageService;
	    
	    @Nested
	    @DisplayName("Valid Tests")
	    class Valid {
	    	@Test
	        void testGetAll() {
	            List<Language> languages = Arrays.asList(
	                    new Language(1, "English"),
	                    new Language(2, "Spanish"),
	                    new Language(3, "French"),
	                    new Language(4, "Chinese")
	            );
	            when(languageRepository.findAll()).thenReturn(languages);

	            List<Language> result = languageService.getAll();

	            assertEquals(4, result.size());
	            verify(languageRepository, times(1)).findAll();
	        }

	        @Test
	        void testGetOne() {
	            Language category = new Language(1, "English");
	            when(languageRepository.findById(1)).thenReturn(Optional.of(category));

	            Optional<Language> result = languageService.getOne(1);

	            assertTrue(result.isPresent());
	            assertEquals(1, result.get().getLanguageId());
	            assertEquals("English", result.get().getName());
	            verify(languageRepository, times(1)).findById(1);
	        }

	        @Test
	        void testAdd() throws DuplicateKeyException, InvalidDataException {
	            Language language = new Language(5, "Russian");
	            when(languageRepository.save(language)).thenReturn(language);

	            Language result = languageService.add(language);

	            assertNotNull(result);
	            assertEquals(5, result.getLanguageId());
	            assertEquals("Russian", result.getName());
	            verify(languageRepository, times(1)).save(language);
	        }

	        @Test
	        void testModify() throws NotFoundException, InvalidDataException {
	            Language language = new Language(1, "Japanese");
	            when(languageRepository.existsById(1)).thenReturn(true);
	            when(languageRepository.save(language)).thenReturn(language);

	            Language result = languageService.modify(language);

	            assertNotNull(result);
	            assertEquals(1, result.getLanguageId());
	            assertEquals("Japanese", result.getName());
	            verify(languageRepository, times(1)).existsById(1);
	            verify(languageRepository, times(1)).save(language);
	        }

	        @Test
	        void testDelete() throws InvalidDataException {
	            Language language = new Language(1, "English");
	            languageService.delete(language);

	            verify(languageRepository, times(1)).delete(language);
	        }

	        @Test
	        void testDeleteById() {
	            languageService.deleteById(1);

	            verify(languageRepository, times(1)).deleteById(1);
	        } 
	       
	    }

	    
	    @Nested
	    @DisplayName("Invalid Tests")
	    class Invalid {
	    	@Test
	        void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
	            when(languageRepository.save(any(Language.class))).thenReturn(null, null);
	            
	            assertThrows(InvalidDataException.class, () -> languageService.add(null));
	            
	            verify(languageRepository, times(0)).save(null);
	        }
	    	
	    	@Test
	    	void testAddDuplicateKeyInvalid() throws DuplicateKeyException, InvalidDataException {
	    		when(languageRepository.findById(1)).thenReturn(Optional.of(new Language(1, "German")));
	    		when(languageRepository.existsById(1)).thenReturn(true);
	    		
	    		assertThrows(DuplicateKeyException.class, () -> languageService.add(new Language(1, "Language Duplicated")));
	    	}
	    }

}
