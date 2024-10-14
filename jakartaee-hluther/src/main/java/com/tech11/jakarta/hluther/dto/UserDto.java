package com.tech11.jakarta.hluther.dto;

import java.time.LocalDate;

import com.tech11.jakarta.hluther.entity.UserEntity;

public class UserDto {

	private Long id;

	private String firstname;

	private String lastname;

	private String email;

	private LocalDate birthday;

	private String password;

	public UserDto() {
		// needed for reflection
	}

	public UserDto(Long id, String firstname, String lastname, String email, LocalDate birthday, String password) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public String getPassword() {
		return password;
	}

	public static UserDto fromEntity(UserEntity userEntity) {

		return new UserDto(userEntity.getId(), userEntity.getFirstname(), userEntity.getLastname(),
				userEntity.getEmail(), userEntity.getBirthday(), userEntity.getPassword());
	}
}
