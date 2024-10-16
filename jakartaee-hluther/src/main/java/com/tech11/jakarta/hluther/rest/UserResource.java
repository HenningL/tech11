package com.tech11.jakarta.hluther.rest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.tech11.jakarta.hluther.dto.UserDto;
import com.tech11.jakarta.hluther.entity.UserEntity;
import com.tech11.jakarta.hluther.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
public class UserResource {

	@Inject
	private UserRepository userRepository;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("{id}")
	public UserDto get(@NotNull @PathParam("id") Long id) {

		UserEntity user = userRepository.getUser(id);

		if (Objects.isNull(user)) {
			throw new NotFoundException("could not find user for id:" + id);
		}
		return UserDto.fromEntity(user);
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
	public Response create(@Valid @NotNull UserDto userDto) {
		// check if id is unset

		if (!Objects.isNull(userDto.getId())) {
			throw new BadRequestException("setting the id is not allowed in this endpoint. id is:" + userDto.getId());
		}

		UserEntity createdEntity = userRepository.create(UserEntity.fromDto(userDto));

		if (Objects.isNull(createdEntity)) {
			throw new InternalServerErrorException("could not create user.");
		}

		return Response.status(Response.Status.CREATED).entity(UserDto.fromEntity(createdEntity)).build();

	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("update")
	public UserDto update(@Valid @NotNull UserDto userDto) {
		return UserDto.fromEntity(userRepository.update(UserEntity.fromDto(userDto)));
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("search")
	public List<UserDto> search(UserDto searchCriteria) {
		return userRepository.search(UserEntity.fromDto(searchCriteria)).stream().map(UserDto::fromEntity)
				.collect(Collectors.toList());
	}
}
