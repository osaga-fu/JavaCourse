package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class ActorDTO implements Serializable {

	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellido")
	private String lastName;

	public static ActorDTO from (Actor source) {
		return new ActorDTO(source.getActorId(),source.getFirstName(), source.getLastName());	
		}
	
	public static Actor from (ActorDTO source) {
		return new Actor(source.getActorId(),source.getFirstName(), source.getLastName());	
		}
}
