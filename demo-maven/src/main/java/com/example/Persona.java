package com.example;

import java.util.Optional;

public class Persona {
	private int id;
	private String nombre;
	private String apellidos;
	
	public Persona(int id, String nombre, String apellidos) {
		super();
		this.id = id;
		setNombre(nombre);
		setApellidos(apellidos);
	}
	
	public Persona(int id, String nombre) {
		super();
		this.id = id;
		setNombre(nombre);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		if (nombre == null || "".equals(nombre.trim()))
			throw new IllegalArgumentException("Falta el nombre");
		this.nombre = nombre;
	}
	
//	public boolean hasApellidos() {
//		return apellidos != null;
//	}
//	
//	public String getApellidos() {
//		return apellidos;
//	}
	
	public Optional<String> getApellidos() {
		return Optional.ofNullable(apellidos);
	}
	
	public void setApellidos(String apellidos) {
		if (nombre == null) 
			throw new NullPointerException("Faltan los apellidos");
		this.apellidos = apellidos;
	}
	
	public void clearApellidos() {
		this.apellidos = null;
	}
	
}
