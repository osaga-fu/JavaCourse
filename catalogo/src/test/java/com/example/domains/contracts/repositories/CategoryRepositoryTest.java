package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.entities.Category;

@SpringBootTest
class CategoryRepositoryTest {
	@Autowired
	CategoryRepository dao;
	
	@Test
	void querySuperficialTest() {
		try {
			dao.findAll();
		} catch (Exception e) {
			fail("JPA mapping Fail: " + e.getMessage());
		}
	}
	@Test
	void modifySuperficialTest() {
		try {
			var item = dao.findAll().getFirst();
			var aux = item.getName();
			item.setName("Category");
			dao.save(item);
			item.setName(aux);
			dao.save(item);
		} catch (Exception e) {
			fail("Update Fail: " + e.getMessage());
		}
	}
	@Test
	void createDeleteTest() {
		try {
			var item = dao.save(new Category(0, "kk"));
			dao.delete(item);
		} catch (Exception ex) {
			fail("Update fail: " + ex.getMessage());
		}
	}

}