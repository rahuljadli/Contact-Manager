package com.smartcontact.dao;

import org.springframework.data.repository.CrudRepository;

import com.smartcontact.entities.User;

public interface UserRepository extends CrudRepository<User,Integer> {
	
	public User findByUserEmail(String email);

}
