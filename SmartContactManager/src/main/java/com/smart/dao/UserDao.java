package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	//public 
}
