package com.jpa.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jpa.test.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	// user define methods which has no implimentation jpa automatically impliment
	// it.
	public List<User> findByName(String name);

	public List<User> findByNameAndCity(String name, String city);

	// fire own query
	// using JPL as like as HQL

	@Query("select u from User u")
	public List<User> getAllUsers();

	// where query
	@Query("select u from User u WHERE u.name =:data1 AND u.city =:data2")
	public List<User> getUserByName(@Param("data1") String name, @Param("data2") String city);

	// Native Query SQL

	@Query(value = "select * from user", nativeQuery = true)
	public List<User> getUsers();
}
