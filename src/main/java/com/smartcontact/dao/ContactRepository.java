package com.smartcontact.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartcontact.entities.Contact;

public interface ContactRepository extends CrudRepository<Contact, Integer> {
	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact> findContactByUser(@Param("userId") int userId,Pageable pageable);
}
