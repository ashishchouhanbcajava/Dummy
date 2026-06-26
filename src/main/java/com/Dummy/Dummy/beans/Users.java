package com.Dummy.Dummy.beans;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Dummy.Dummy.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Users implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@NotEmpty(message = "contact is required")
	private String contact;
	@Transient
	private String token;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Long id, String username, String password, Role role, String token, String contact) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.token = token;
		this.contact = contact;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		authorities.add(new SimpleGrantedAuthority(role.name()));

		Set<SimpleGrantedAuthority> permissions = role.getPermissions().stream()
				.map(e -> new SimpleGrantedAuthority(e.name())).collect(Collectors.toSet());
		authorities.addAll(permissions);
		return authorities;
	}

}
