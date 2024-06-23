package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyDouble;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.Smoke;

@DisplayName("Pruebas clase calculadora")
//@TestMethodOrder(MethodOrderer.class)
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
			
//			@Test
			@DisplayName("Suma dos enteros")
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			void testAdd() {
				Calculadora calculadora = new Calculadora();
				
				assertEquals(3, calculadora.add(1, 2));
				assertEquals(0, calculadora.add(0, 0), "caso 0, 0");
			}
			
			@ParameterizedTest(name = "Caso {index}: {0} + {1} = {2}")
			@DisplayName("Suma dos enteros parametizada")
			@CsvSource(value = {"1,2,3","3,-1,2","-1,2,1"})
			void testAdd3(double operando1, double operando2, double resultado) {
				Calculadora calculadora = new Calculadora();
				
				assertEquals(resultado, calculadora.add(operando1, operando2));
			}
			
			@Test
			@Tag("smoke")
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
			@Smoke
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
		
		@Test
		void simula() {
			Calculadora calculadora = mock(Calculadora.class);
			when(calculadora.add(anyDouble(), anyDouble())).thenReturn(3.0).thenReturn(4.0);
			
			assertEquals(3, calculadora.add(2.0, 2.0));
			assertEquals(4, calculadora.add(12.2, 2.2));
		}
	}
	
	
	
	

}
