package com.Rest.Book.dao;

import org.springframework.data.repository.CrudRepository;

import com.Rest.Book.entities.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

	public Book findById(int bookId);
}
