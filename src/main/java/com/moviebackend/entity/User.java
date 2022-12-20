package com.moviebackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String username;
	private String password;
	private String avatar;
	
	@Column(length = 32, columnDefinition = "varchar(32) default 'participate'")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	public User(Integer id) {
		this.id = id;
	}
	
	public boolean isAdmin() {
		return this.role == UserRole.admin;
	}

}
