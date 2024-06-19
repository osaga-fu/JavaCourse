package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas clase calculadora")
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

	@Nested
	@DisplayName("Add method")
	class Add {
		
		@Nested
		class OK{
			
			@Test
			@DisplayName("Suma dos enteros")
			void testAdd() {
				Calculadora calculadora = new Calculadora();
				
				assertEquals(3, calculadora.add(1, 2));
				assertEquals(0, calculadora.add(0, 0), "caso 0, 0");
			}
			
			@Test
			@DisplayName("Suma dos flotantes")
			void testAdd2() {
				Calculadora calculadora = new Calculadora();
				
				double result = calculadora.add(0.1, 0.2);
				
				assertEquals(0.3, result);
			}
			
		}
		
		@Nested
		class KO{
			
		}
	}
	
	@Nested
	@DisplayName("Divide method")
	class Divide {
		
		@Nested
		class OK {
			@Test
			@DisplayName("División de enteros")
			void testDivInt() {
				Calculadora calculadora = new Calculadora();
			
				double result = calculadora.divide(1, 2);
			
				assertEquals(0.5, result);
			}

			@Test
			@DisplayName("División de float")
			void testDivReal() {
				Calculadora calculadora = new Calculadora();
			
				double result = calculadora.divide(3.0, 2.0);
			
				assertEquals(1.5, result);
			}
		}
		
		@Nested
		class KO{
			@Test
			@DisplayName("División entre 0 salta excepción")
			void testDivRealKO() {
				Calculadora calculadora = new Calculadora();
				
				assertThrows(ArithmeticException.class, () -> calculadora.divide(3.0, 0));
			}
		}
	}
	
	
	
	

}
