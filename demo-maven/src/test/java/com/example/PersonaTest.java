package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PersonaTest {

	@Nested
	@DisplayName("Proceso de instanciacion")
	class Create {
		@Nested
		class OK {
			@Test
			@DisplayName("Creacion de persona solo con nombre")
			void onlyName() {
				var persona = new Persona(1, "Pepe");
				
				assertNotNull(persona);
				assertAll("Persona",
						()-> assertEquals(1, persona.getId(), "id"),
						()-> assertEquals("Pepe", persona.getNombre(), "nombre"),
						()-> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}
			
			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = {"1,Pepe","2,Mari", "3,María"})
			@DisplayName("Creacion de persona solo con nombre parametizada")
			void onlyNameParam(int id, String nombre) {
				var persona = new Persona(id, nombre);
				
				assertNotNull(persona);
				assertAll("Persona",
						()-> assertEquals(id, persona.getId(), "id"),
						()-> assertEquals(nombre, persona.getNombre(), "nombre"),
						()-> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}
		}
		
		@Nested
		class KO {
			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = {"1,","2,' '", "3,''"})
			@DisplayName("Creacion de persona con nombre invalido parametizada")
			void invalidNameParam(int id, String nombre) {
				assertThrows(Exception.class, ()-> new Persona(id, nombre));
			}
		}
	}

}
