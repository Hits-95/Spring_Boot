package com.jpa.test;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.test.dao.UserRepository;
import com.jpa.test.entities.User;

@SpringBootApplication
public class BootJpaExampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BootJpaExampleApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);

		// save section

//		User user1 = new User("Ganesh Nikam", "At Chichgaon", "he is doctor.");
//		User user2 = new User("Hitesh Ahire", "At Dabli", "I'm programmer.");
//		User user3 = new User("Nikita Morankr", "At Pachora	", "She is good english speaker.");
//
//		// make list of users
//		List<User> users = List.of(user1, user2, user3);
//
//		// save multiple users users...
//		Iterable<User> saveAllResult = userRepository.saveAll(users);
//
//		saveAllResult.forEach(user -> {
//			System.out.println(user);
//		});
//
//		// save single user
//		User save_user = userRepository.save(user);
//		System.out.println(save_user);

		// update section
//		Optional<User> optional = userRepository.findById(9);
//		User user = optional.get();
//		user.setCity("at nashik , pune");
//		
//		User save = userRepository.save(user);
//		
//		System.out.println(save);

		// get all data
//		Iterable<User> findAll = userRepository.findAll();
//		
//		findAll.forEach(user->{
//			System.out.println(user);
//		});

		// delete one
		// userRepository.deleteById(8);
		// delete all
		// userRepository.deleteAll();

		// pass collectin also here
		// userRepository.deleteAll(users);

		/*
		 *** calling custom finder methods
		 */
		// List<User> findByName = userRepository.findByName("Hitesh Ahire");
//		List<User> findByName = userRepository.findByNameAndCity("Ganesh Nikam", "At Chichgaon");
//		findByName.forEach(data -> {
//			System.out.println(data);
//		});

		// get all users
		List<User> allUsers = userRepository.getAllUsers();
		allUsers.forEach(e -> {
			System.out.println(e);
		});
		System.out.println("------------------------------");
		// get single user data
		List<User> userByName = userRepository.getUserByName("Ganesh Nikam", "At Chichgaon");
		userByName.forEach(data -> {
			System.out.println(data);
		});
		System.out.println("------------------------------");
		// call nativee Query
		List<User> users = userRepository.getUsers();
		users.forEach(data -> {
			System.out.println(data);
		});

	}

}
