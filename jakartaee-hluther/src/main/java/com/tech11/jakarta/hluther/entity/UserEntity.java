package com.tech11.jakarta.hluther.entity;

import java.time.LocalDate;

import com.tech11.jakarta.hluther.dto.UserDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity(name = "USER")
@Table(name = "USERS")
@NamedQuery(name = "getalluser", query = "select u from USER u")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstname;

	private String lastname;

	private String email;

	private LocalDate birthday;

	private String password;

	@Version
	private int version;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public static UserEntity fromDto(UserDto userDto) {
		UserEntity entity = new UserEntity();
		entity.id = userDto.getId();
		entity.setEmail(userDto.getEmail());
		entity.setFirstname(userDto.getFirstname());
		entity.setLastname(userDto.getLastname());
		entity.setPassword(userDto.getPassword());
		entity.setBirthday(userDto.getBirthday());
		return entity;
	}

}
