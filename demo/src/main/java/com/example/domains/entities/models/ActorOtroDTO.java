package com.example.domains.entities.models;

import lombok.Data;

@Data
public class ActorOtroDTO {
	
	private int id;
	private String nombre;
	private String apellidos;
	
	public ActorOtroDTO(int actorId, String firstName, String lastName) {
		super();
		this.id = actorId;
		this.nombre = firstName;
		this.apellidos = lastName;
	}
}
