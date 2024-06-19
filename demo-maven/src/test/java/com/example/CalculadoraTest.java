package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdd() {
		Calculadora calculadora = new Calculadora();
		
		double result = calculadora.add(1, 2);
		
		assertEquals(3, result);
	}
	
	@Test
	void testAdd2() {
		Calculadora calculadora = new Calculadora();
		
		double result = calculadora.add(0.1, 0.2);
		
		assertEquals(0.3, result);
	}
	
	@Test
	void testDivInt() {
		Calculadora calculadora = new Calculadora();
		
		double result = calculadora.divide(1, 2);
		
		assertEquals(0.5, result);
	}

	@Test
	void testDivReal() {
		Calculadora calculadora = new Calculadora();
		
		double result = calculadora.divide(3.0, 2.0);
		
		assertEquals(1.5, result);
	}
	
	@Test
	void testDivRealKO() {
		Calculadora calculadora = new Calculadora();
		
		
		assertThrows(ArithmeticException.class, () -> calculadora.divide(3.0, 0));
	}
}
