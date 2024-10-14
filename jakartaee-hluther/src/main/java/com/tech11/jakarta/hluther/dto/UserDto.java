package com.tech11.jakarta.hluther.dto;

import java.time.LocalDate;

import com.tech11.jakarta.hluther.entity.UserEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserDto {

	private Long id;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	@NotNull
	@Email
	private String email;

	@NotNull
	private LocalDate birthday;

	/*
	 * password needs to be encoded and salted to not be restorable and populated to
	 * public out of the realm. for now it's simply done for easy impl. passwords
	 * should never be able to be reconstructed from db-data.default services for
	 * that topic are expectable.
	 */
	@NotNull
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
