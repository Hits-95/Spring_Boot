package com.Rest.Book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	// get all books handler
	@ResponseBody
	// @RequestMapping(value = "/books", method = RequestMethod.GET)
	@GetMapping("/books")
	public List<Book> getBooks() {
		return this.bookService.getAllBooks();
	}

	// get single book handler
	@ResponseBody
	// @RequestMapping(value = "/books", method = RequestMethod.GET)
	@GetMapping("/books/{bookId}")
	public Book getBook(@PathVariable("bookId") int id) {
		return this.bookService.getBookById(id);
	}

	// add single book handler
	@ResponseBody
	@PostMapping("/books")
	public Book addBook(@RequestBody Book book) {
		System.out.println(book);
		return this.bookService.addBook(book);

	}

	// delete book handler
	@ResponseBody
	@DeleteMapping("/books/{bookId}")
	public void deleteBook(@PathVariable("bookId") int id) {
		this.bookService.deleteBook(id);
	}

	// update book handler
	@ResponseBody
	@PutMapping("/books/{bookid}")
	public Book updateBook(@RequestBody Book book, @PathVariable("bookid") int bookid) {
		return this.bookService.updateBook(book, bookid);
	}
}
