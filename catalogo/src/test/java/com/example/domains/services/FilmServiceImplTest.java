package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
public class FilmServiceImplTest {

    @MockBean
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;
    
    @Nested
    @DisplayName("Valid Tests")
    class Valid {
    	@Test
        void testGetAll() {
            List<Film> films = Arrays.asList(
                    new Film(1, "Batman begins"),
                    new Film(2, "Titanic"),
                    new Film(3, "Todo sobre mi madre")
            );
            when(filmRepository.findAll()).thenReturn(films);

            List<Film> result = filmService.getAll();

            assertEquals(3, result.size());
            verify(filmRepository, times(1)).findAll();
        }

        @Test
        void testGetOne() {
            Film actor = new Film(1, "Batman begins");
            when(filmRepository.findById(1)).thenReturn(Optional.of(actor));

            Optional<Film> result = filmService.getOne(1);

            assertTrue(result.isPresent());
            assertEquals(1, result.get().getFilmId());
            assertEquals("Batman", result.get().getTitle());
            verify(filmRepository, times(1)).findById(1);
        }

        @Test
        void testAdd() throws DuplicateKeyException, InvalidDataException {
            Film film = new Film(4, "Torrente");
            when(filmRepository.save(film)).thenReturn(film);

            Film result = filmService.add(film);

            assertNotNull(result);
            assertEquals(4, result.getFilmId());
            assertEquals("ANA", result.getTitle());
            verify(filmRepository, times(1)).save(film);
        }

        @Test
        void testModify() throws NotFoundException, InvalidDataException {
            Film film = new Film(1, "Batman Begins");
            when(filmRepository.existsById(1)).thenReturn(true);
            when(filmRepository.save(film)).thenReturn(film);

            Film result = filmService.modify(film);

            assertNotNull(result);
            assertEquals(1, result.getFilmId());
            assertEquals("Batman Begins", result.getTitle());
            verify(filmRepository, times(1)).existsById(1);
            verify(filmRepository, times(1)).save(film);
        }

        @Test
        void testDelete() throws InvalidDataException {
            Film film = new Film(1, "Tenet");
            filmService.delete(film);

            verify(filmRepository, times(1)).delete(film);
        }

        @Test
        void testDeleteById() {
            filmService.deleteById(1);

            verify(filmRepository, times(1)).deleteById(1);
        }
    } 
    
    @Nested
    @DisplayName("Invalid Tests")
    class Invalid {
    	@Test
        void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
            when(filmRepository.save(any(Film.class))).thenReturn(null, null);
            
            assertThrows(InvalidDataException.class, () -> filmService.add(null));
            
            verify(filmRepository, times(0)).save(null);
        }
    	
    	@Test
    	void testAddDuplicateKeyInvalid() throws DuplicateKeyException, InvalidDataException {
    		when(filmRepository.findById(1)).thenReturn(Optional.of(new Film(1, "Tenet")));
    		when(filmRepository.existsById(1)).thenReturn(true);
    		
    		assertThrows(DuplicateKeyException.class, () -> filmService.add(new Film(1, "Duplicate")));
    	}

    }
}

