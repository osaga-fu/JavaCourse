package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
		}
		
		@Nested
		class KO {
			
		}
	}

}
