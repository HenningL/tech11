package com.tech11.jakarta.hluther.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.tech11.jakarta.hluther.entity.UserEntity;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@Stateless
public class UserRepository {

	@PersistenceContext
	private EntityManager em;

	public UserEntity getUser(Long id) {
		return em.find(UserEntity.class, id);
	}

	public List<UserEntity> getUsers() {
		return em.createNamedQuery("getalluser", UserEntity.class).getResultList();
	}

	public UserEntity create(UserEntity entity) {
		em.persist(entity);
		em.flush();
		em.refresh(entity);
		return entity;
	}

	@Transactional
	public UserEntity update(UserEntity updateEntity) {

		UserEntity existingEntity = getUser(updateEntity.getId());
		if (Objects.isNull(existingEntity)) {
			throw new NotFoundException("could not find user for id:" + updateEntity.getId());
		}

		existingEntity.setFirstname(updateEntity.getFirstname());
		existingEntity.setLastname(updateEntity.getLastname());
		existingEntity.setBirthday(updateEntity.getBirthday());
		existingEntity.setEmail(updateEntity.getEmail());
		existingEntity.setPassword(updateEntity.getPassword());

		em.merge(existingEntity);
		em.flush();
		em.refresh(existingEntity, LockModeType.OPTIMISTIC);
		return existingEntity;
	}

	public List<UserEntity> search(UserEntity searchCrteria) {
		return Arrays.asList(searchCrteria);
	}

}
