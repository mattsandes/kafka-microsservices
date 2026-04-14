package com.example.demo.data;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients_tb")
public class Clients {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String clientName;
	private String email;
	
	public Clients() {
	}

	public Clients(UUID id, String clientName, String email) {
		this.id = id;
		this.clientName = clientName;
		this.email = email;
	}

	public Clients(String clientName, String email) {
		super();
		this.clientName = clientName;
		this.email = email;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientName, email, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clients other = (Clients) obj;
		return Objects.equals(clientName, other.clientName) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id);
	}
}
