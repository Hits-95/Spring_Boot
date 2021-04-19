package com.Rest.Book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Rest.Book.entities.Book;
import com.Rest.Book.services.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@ResponseBody
	// @RequestMapping(value = "/books", method = RequestMethod.GET)
	@GetMapping("/books")
	public List<Book> getBooks() {
		return this.bookService.getAllBooks();
	}

	@ResponseBody
	// @RequestMapping(value = "/books", method = RequestMethod.GET)
	@GetMapping("/books/{id}")
	public Book getBook(@PathVariable("id") int id) {
		return this.bookService.getBookById(id);
	}

	@ResponseBody
	@PostMapping("/books")
	public Book addBook(@RequestBody Book book) {
		System.out.println(book);
		return this.bookService.addBook(book);

	}
}
