package com.Rest.Book.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigData.Option;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Book>> getBooks() {

		List<Book> allBooks = this.bookService.getAllBooks();

		if (allBooks.size() <= 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.of(Optional.of(allBooks));

	}

	// get single book handler
	@ResponseBody
	// @RequestMapping(value = "/books", method = RequestMethod.GET)
	@GetMapping("/books/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable("bookId") int id) {

		Book book = this.bookService.getBookById(id);

		if (book == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.of(Optional.of(book));
	}

	// add single book handler
	@ResponseBody
	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {

		Book addBook = null;

		try {
			addBook = bookService.addBook(book);
			// if (addBook != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(addBook);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
		// return ResponseEntity.of(Optional.of());
		// return ResponseEntity.of(Optional.of(addBook));
	}

	// delete book handler
	@ResponseBody
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int id) {
		try {
			this.bookService.deleteBook(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	// update book handler
	@ResponseBody
	@PutMapping("/books/{bookid}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("bookid") int bookid) {
		try {
			Book updateBook = this.bookService.updateBook(book, bookid);
			return ResponseEntity.ok().body(updateBook);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
