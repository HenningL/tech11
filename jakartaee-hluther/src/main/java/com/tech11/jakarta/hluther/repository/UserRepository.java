package com.tech11.jakarta.hluther.repository;

import java.util.Arrays;
import java.util.List;

import com.tech11.jakarta.hluther.entity.UserEntity;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
	
	public UserEntity update(UserEntity entity) {
		em.merge(entity);
		em.flush();
		em.refresh(entity);
		return entity;
	}

	public List<UserEntity> search(UserEntity searchCrteria) {
		return Arrays.asList(searchCrteria);
	}

}
