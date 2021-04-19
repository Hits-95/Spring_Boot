package com.Rest.Book.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Rest.Book.entities.Book;

@Service
//@Component
public class BookService {

	private static List<Book> list = new ArrayList<Book>();

	static {
		list.add(new Book(1, "Python complite", "Ganesh Nikam"));
		list.add(new Book(2, "Java", "Nikita"));
		list.add(new Book(3, "Angular", "Pradhunya"));
		list.add(new Book(4, "React", "Vrushali"));
		list.add(new Book(5, "Spring Boot", "Komal"));

	}

	// get all books
	public List<Book> getAllBooks() {
		return list;
	}

	// get single book
	public Book getBookById(int id) {

		return list.stream().filter(data -> data.getId() == id).findFirst().get();
	}

	// adding the book
	public Book addBook(Book book) {
		list.add(book);
		return book;
	}
}