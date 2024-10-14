package com.tech11.jakarta.hluther.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.tech11.jakarta.hluther.dto.UserDto;
import com.tech11.jakarta.hluther.entity.UserEntity;
import com.tech11.jakarta.hluther.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("user")
public class UserResource {
	
	 @Inject
	 private UserRepository userRepository;


	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{id}")
	public UserDto get(@PathParam("id") Long id) {
		return UserDto.fromEntity(userRepository.getUser(id));
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("list")
	public List<UserDto> list() {
		return userRepository.getUsers().stream().map(UserDto::fromEntity).collect(Collectors.toList());
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("create")
	public UserDto create(UserDto userDto) {
		//check if id is unset
		return UserDto.fromEntity(userRepository.create(UserEntity.fromDto(userDto)));
	}
	
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("update")
	@Transactional
	public UserDto update(UserDto userDto) {
		//check if id is set
		
		UserEntity entity = userRepository.getUser(userDto.getId());
		entity.setFirstname(userDto.getFirstname());
		entity.setLastname(userDto.getLastname());
		entity.setBirthday(userDto.getBirthday());
		entity.setEmail(userDto.getEmail());
		entity.setPassword(userDto.getPassword());
		return UserDto.fromEntity(userRepository.update(entity));
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("search")
	public List<UserDto> search(UserDto searchCrteria) {
		return userRepository.search(searchCrteria);
	}
}