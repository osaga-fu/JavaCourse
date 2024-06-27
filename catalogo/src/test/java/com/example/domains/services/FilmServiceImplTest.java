package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@DisplayName("FilmServiceImpl Tests")
class FilmServiceImplTest {
    private FilmRepository dao;
    private FilmService service;

    @BeforeEach
    void setUp() {
        dao = mock(FilmRepository.class);
        service = new FilmServiceImpl(dao);
    }

    @Nested
    @DisplayName("getByProjection Tests")
    class GetByProjectionTests {

        @Test
        @DisplayName("Get all by projection without sort")
        void testGetByProjection() {
            List<Film> films = List.of(new Film(1, "Title", new Language(1, "English"), (byte) 1, BigDecimal.valueOf(1.99), BigDecimal.valueOf(19.99)));
            when(dao.findAllBy(Film.class)).thenReturn(films);

            List<Film> result = service.getByProjection(Film.class);

            assertAll("GetByProjection",
                    () -> assertNotNull(result, "Result should not be null"),
                    () -> assertEquals(films.size(), result.size(), "Size should match"));
        }

        @Test
        @DisplayName("Get all by projection with sort")
        void testGetByProjectionWithSort() {
            List<Film> films = List.of(new Film(1, "Title", new Language(1, "English"), (byte) 1, BigDecimal.valueOf(1.99), BigDecimal.valueOf(19.99)));
            Sort sort = Sort.by("title");
            when(dao.findAllBy(sort, Film.class)).thenReturn(films);

            Iterable<Film> result = service.getByProjection(sort, Film.class);

            assertNotNull(result, "Result should not be null");
        }

        @Test
        @DisplayName("Get all by projection with pageable")
        void testGetByProjectionWithPageable() {
            List<Film> films = List.of(new Film(1, "Title", new Language(1, "English"), (byte) 1, BigDecimal.valueOf(1.99), BigDecimal.valueOf(19.99)));
            Pageable pageable = PageRequest.of(0, 10);
            Page<Film> page = new PageImpl<>(films, pageable, films.size());
            when(dao.findAllBy(pageable, Film.class)).thenReturn(page);

            Page<Film> result = service.getByProjection(pageable, Film.class);

            assertAll("GetByProjectionPageable",
                    () -> assertNotNull(result, "Result should not be null"),
                    () -> assertEquals(films.size(), result.getTotalElements(), "Total elements should match"));
        }
    }

    @Nested
    @DisplayName("CRUD Tests")
    class CrudTests {

        @Test
        @DisplayName("Get all films")
        void testGetAll() {
            List<Film> films = List.of(new Film(1, "Title", new Language(1, "English"), (byte) 1, BigDecimal.valueOf(1.99), BigDecimal.valueOf(19.99)));
            when(dao.findAll()).thenReturn(films);

            List<Film> result = service.getAll();

            assertAll("GetAll",
                    () -> assertNotNull(result, "Result should not be null"),
                    () -> assertEquals(films.size(), result.size(), "Size should match"));
        }

        @Test
        @DisplayName("Get one film by ID")
        void testGetOne() {
            Film film = new Film(1, "Title", new Language(1, "English"), (byte) 1, BigDecimal.valueOf(1.99), BigDecimal.valueOf(19.99));
            when(dao.findById(1)).thenReturn(Optional.of(film));

            Optional<Film> result = service.getOne(1);

            assertAll("GetOne",
                    () -> assertTrue(result.isPresent(), "Result should be present"),
                    () -> assertEquals(film.getFilmId(), result.get().getFilmId(), "Film ID should match"));
        }


        @Test
        @DisplayName("Delete film")
        void testDelete() throws InvalidDataException {
            Film film = new Film(1, "Title", new Language(1, "English"), (byte) 1, BigDecimal.valueOf(1.99), BigDecimal.valueOf(19.99));

            assertDoesNotThrow(() -> service.delete(film));
        }

        @Test
        @DisplayName("Delete film by ID")
        void testDeleteById() {
            assertDoesNotThrow(() -> service.deleteById(1));
        }
    }

    @Nested
    @DisplayName("InvalidDataException Tests")
    class InvalidDataExceptionTests {

        @Test
        @DisplayName("Add film with null")
        void testAddNull() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Modify film with null")
        void testModifyNull() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Delete film with null")
        void testDeleteNull() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.delete(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }
    }
}
