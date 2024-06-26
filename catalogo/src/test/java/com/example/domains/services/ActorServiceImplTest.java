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

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
public class ActorServiceImplTest {

    @MockBean
    private ActorRepository actorRepository;

    @Autowired
    private ActorService actorService;
    
    @Nested
    @DisplayName("Valid Tests")
    class Valid {
    	@Test
        void testGetAll() {
            List<Actor> actors = Arrays.asList(
                    new Actor(1, "JAMES", "SMITH"),
                    new Actor(2, "PEPI", "FERNANDEZ"),
                    new Actor(3, "SAM", "FRANCISCO")
            );
            when(actorRepository.findAll()).thenReturn(actors);

            List<Actor> result = actorService.getAll();

            assertEquals(3, result.size());
            verify(actorRepository, times(1)).findAll();
        }

        @Test
        void testGetOne() {
            Actor actor = new Actor(1, "JAMES", "SMITH");
            when(actorRepository.findById(1)).thenReturn(Optional.of(actor));

            Optional<Actor> result = actorService.getOne(1);

            assertTrue(result.isPresent());
            assertEquals(1, result.get().getActorId());
            assertEquals("JAMES", result.get().getFirstName());
            assertEquals("SMITH", result.get().getLastName());
            verify(actorRepository, times(1)).findById(1);
        }

        @Test
        void testAdd() throws DuplicateKeyException, InvalidDataException {
            Actor actor = new Actor(4, "ANA", "JUAREZ");
            when(actorRepository.save(actor)).thenReturn(actor);

            Actor result = actorService.add(actor);

            assertNotNull(result);
            assertEquals(4, result.getActorId());
            assertEquals("ANA", result.getFirstName());
            assertEquals("JUAREZ", result.getLastName());
            verify(actorRepository, times(1)).save(actor);
        }

        @Test
        void testModify() throws NotFoundException, InvalidDataException {
            Actor actor = new Actor(1, "JUAN", "SMITH");
            when(actorRepository.existsById(1)).thenReturn(true);
            when(actorRepository.save(actor)).thenReturn(actor);

            Actor result = actorService.modify(actor);

            assertNotNull(result);
            assertEquals(1, result.getActorId());
            assertEquals("JUAN", result.getFirstName());
            assertEquals("SMITH", result.getLastName());
            verify(actorRepository, times(1)).existsById(1);
            verify(actorRepository, times(1)).save(actor);
        }

        @Test
        void testDelete() throws InvalidDataException {
            Actor actor = new Actor(1, "JAMES", "SMITH");
            actorService.delete(actor);

            verify(actorRepository, times(1)).delete(actor);
        }

        @Test
        void testDeleteById() {
            actorService.deleteById(1);

            verify(actorRepository, times(1)).deleteById(1);
        }
    } 
    
    @Nested
    @DisplayName("Invalid Tests")
    class Invalid {
    	@Test
        void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
            when(actorRepository.save(any(Actor.class))).thenReturn(null, null);
            
            assertThrows(InvalidDataException.class, () -> actorService.add(null));
            
            verify(actorRepository, times(0)).save(null);
        }
    	
    	@Test
    	void testAddDuplicateKeyInvalid() throws DuplicateKeyException, InvalidDataException {
    		when(actorRepository.findById(1)).thenReturn(Optional.of(new Actor(1, "JAMES", "SMITH")));
    		when(actorRepository.existsById(1)).thenReturn(true);
    		
    		assertThrows(DuplicateKeyException.class, () -> actorService.add(new Actor(1, "PP", "ILLO")));
    	}

    }
}

