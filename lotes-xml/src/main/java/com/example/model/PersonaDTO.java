package com.example.model;

import lombok.Data;

@Data
public class PersonaDTO {
	private long id;
	private String nombre;
	private String apellidos;
	private String correo;
	private String sexo;
	private String ip;
}