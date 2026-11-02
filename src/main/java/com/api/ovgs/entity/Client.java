package com.api.ovgs.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="tb_cliente")
public class Client { // Cliente

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Key primary
	@JoinColumn(name ="id")
	private Integer id;

	@Column(nullable = false, length = 150) // o campo é obrigatório; no banco, ele não pode ser NULL
	private String name;

	@Column(length = 150, unique = true) // unique = true: não pode haver dois registros com o mesmo documento
	private String email;

	@Column(nullable = false)
	private boolean active;

	public Client() {}

	public Client(Integer id, String name, String email, boolean active) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
