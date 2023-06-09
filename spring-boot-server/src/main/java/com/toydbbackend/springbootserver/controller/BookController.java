package com.toydbbackend.springbootserver.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.toydbbackend.springbootserver.model.Book;
import com.toydbbackend.springbootserver.repository.BookRepository;
import com.toydbbackend.springbootserver.service.BookService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/book") // This means URL's start with /book (after Application path)

public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired // This means to get the bean called bookRepository
               // Which is auto-generated by Spring, we will use it to handle the data
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/get")
    public ResponseEntity<Iterable<Book>> getAllBooks(@RequestParam(required = false) String bookID) {
        if (bookID != null && !bookID.isEmpty()) {
            List<Book> filteredBooks = bookRepository.findByBookID(bookID);
            if (filteredBooks.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(filteredBooks);
            }
        } else {
            Iterable<Book> allBooks = bookRepository.findAll();
            return ResponseEntity.ok(allBooks);
        }
    }

    /*
     * This method is used to add new books, it should have the following behaviors:
     * 
     * 1. if the bookID does NOT exists, simply creates new book
     * 2. if the bookID already exists, check attributes, if they do not match, add
     * this record to errors
     * 
     * NOTE: in request body, only the 'bookID' is the must, others can be NULL
     * 
     * in the end it should return JSON with 3 categories: newBooks, totalUpdates,
     * Errors
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public ResponseEntity<?> addNewBook(@RequestBody List<Book> newBooks) {
        List<Book> addedBooks = new ArrayList<>();
        List<Book> updatedBooks = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        for (Book newBook : newBooks) {
            try {
                // set the total books to 1 if it is not set
                if (newBook.getTotal() == null) {
                    newBook.setTotal(1);
                }
                Optional<Book> existingBook = bookRepository.findById(newBook.getBookID());
                if (existingBook.isPresent()) {
                    Book book = existingBook.get();
                    if (!newBook.partialMatch(book)) {
                        errors.put(newBook.getBookID(), "Error: book information does not match");
                    } else {
                        book.setTotal(book.getTotal() + newBook.getTotal());
                        log.info("TOTAL OF BOOK: {}", book.getTotal());
                        Book savedBook = bookService.updateBook(book);
                        updatedBooks.add(savedBook);
                    }
                } else {
                    // if the book does not exist, add it to database
                    log.info("ADD NEW BOOK TO DATABASE");
                    Book savedBook = bookService.addNewBook(newBook);
                    addedBooks.add(savedBook);
                }
            } catch (DataIntegrityViolationException e) {
                errors.put(newBook.getBookID(), "Error: data Integrity violation, title cannot be null");
            } catch (Exception e) {
                log.error("An error occurred while adding a new book", e);
                errors.put(newBook.getBookID(), "Error: an unknown error occurred");
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("addedBooks", addedBooks);
        response.put("updatedBooks", updatedBooks);
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // TODO: specify update behavior and write test code
    // Put method is only used to update book information, and should NOT be used to
    // update total/stock

    @DeleteMapping(path = "/delete/{bookID}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookID") String bookID) {
        try {
            Optional<Book> existingBook = bookRepository.findById(bookID);
            if (existingBook.isPresent()) {
                Book deletedBook = existingBook.get();
                bookRepository.deleteById(bookID);
                return ResponseEntity.ok().body(deletedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
