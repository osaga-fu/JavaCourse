package com.example.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.entities.Actor;

@DataJpaTest
class ActorRepositoryMemoryTest {
	@Autowired
	private TestEntityManager em;

	@Autowired
	ActorRepository actorRepository;

	@BeforeEach
	void setUp() throws Exception {
		var item = new Actor(0, "PEPITO", "GRILLO");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
		item = new Actor(0, "JAMES", "COTON");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
		item = new Actor(0, "STEVE", "TAN");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
	}
	
	@Test
	void testGetAll_isNotEmpty() {
		var result = actorRepository.findAll();
		assertThat(result.size()).isEqualTo(3);
		assertThat(actorRepository.findTop5ByLastNameStartingWithOrderByFirstNameDesc("C").size()).isEqualTo(1);
		assertThat(actorRepository.findByJPQL(1).size()).isEqualTo(3);
	}

	@Test
	void testGetOne() {
		var item = actorRepository.findById(actorRepository.findAll().get(0).getActorId());
		assertThat(item.isPresent()).isTrue();
		assertEquals("PEPITO", item.get().getFirstName());
	}

	
	@Test
	void findBySQLTest() {
		assertThat(actorRepository.findBySQL(1).size()).isGreaterThan(0);
	}

}