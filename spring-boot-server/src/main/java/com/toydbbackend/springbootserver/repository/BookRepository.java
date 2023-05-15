package com.toydbbackend.springbootserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toydbbackend.springbootserver.model.Book;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitle(String title);
    List<Book> findByBookID(String bookID);
}
