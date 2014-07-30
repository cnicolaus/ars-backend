package com.prodyna.pac.ars.backend.user.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.ars.backend.common.client.AbstractEntity;
import com.prodyna.pac.ars.backend.common.client.UserRole;

@Entity
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "user.findAll", query = "FROM User"),
		@NamedQuery(name = "user.findByUsername", query = "FROM User where username = ?") })
public class User extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, columnDefinition = "VARCHAR(16)", unique = true)
	@NotNull
	@Size(min = 5, max = 16)
	private String username;

	@Column(nullable = false, columnDefinition = "VARCHAR(32)")
	@NotNull
	@Size(min = 5, max = 32)
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
