package com.toydbbackend.springbootserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toydbbackend.springbootserver.model.Book;
import com.toydbbackend.springbootserver.repository.BookRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Book addNewBook(Book newBook) {
        // Save the new book
        Book savedBook = bookRepository.save(newBook);

        // Flush changes to the database
        entityManager.flush();

        // Refresh the saved book with its current state in the database
        entityManager.refresh(savedBook);

        return savedBook;
    }

    @Transactional
    public Book updateBook(Book book) {
        // Update the book
        Book updatedBook = bookRepository.save(book);

        // Flush changes to the database
        entityManager.flush();

        // Refresh the updated book with its current state in the database
        entityManager.refresh(updatedBook);

        return updatedBook;
    }

}
