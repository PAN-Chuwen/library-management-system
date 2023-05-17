package com.toydbbackend.springbootserver.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.toydbbackend.springbootserver.model.Borrow;
import com.toydbbackend.springbootserver.repository.BookRepository;
import com.toydbbackend.springbootserver.repository.BorrowRepository;
import com.toydbbackend.springbootserver.repository.CardRepository;

@Controller
@RequestMapping(path = "/borrow")

public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BookRepository bookRepository;

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    // return all borrow records for specific cardID, including basic book info and
    // borrow/return date
    @GetMapping(path = "/records/{cardID}")
    public ResponseEntity<?> getBorrowRecords(@PathVariable("cardID") String cardID) {

        List<Borrow> borrows = borrowRepository.findByCardCardID(cardID);
        return ResponseEntity.ok(borrows);
    }

    @GetMapping(path = "/records")
    public ResponseEntity<?> getAllBorrowRecords() {
        log.info("getting all borrow cardID");
        List<Borrow> borrows = borrowRepository.findAll();
        return ResponseEntity.ok(borrows);
    }

    // allow user to borrow books, check 1. if the limit of 5 books has been reached
    // 2. if the book is available
    @PostMapping(path = "/user/{cardID}/{bookID}")
    public ResponseEntity<?> borrowBook(@PathVariable("cardID") String cardID, @PathVariable("bookID") String bookID) {
        log.info("checking if allowed to borrow the book");
        // could go through the borrow table to check if the user has reached the limit
        // of 5 books, but this approach is too slow
        // instead create custom query methods in Repository code

        if (borrowRepository.countByCardCardIDAndNotReturned(cardID) >= 5) {
            log.info("borrow limits: 5 books max");
            return ResponseEntity.badRequest().body("You have reached the limit of 5 books");
        } else if (!bookRepository.findById(bookID).isPresent()) {
            return ResponseEntity.badRequest().body("The book is not available");
        } else {
            Borrow savedBorrowRecord = new Borrow();
            savedBorrowRecord.setCard(cardRepository.findById(cardID).get());
            savedBorrowRecord.setBook(bookRepository.findById(bookID).get());
            savedBorrowRecord.setBorrowDate(LocalDate.now());
            savedBorrowRecord.setReturnDate(null);

            savedBorrowRecord = borrowRepository.save(savedBorrowRecord); // the borrowID field is automatically updated
            return ResponseEntity.ok(savedBorrowRecord);
        }
    }
}
