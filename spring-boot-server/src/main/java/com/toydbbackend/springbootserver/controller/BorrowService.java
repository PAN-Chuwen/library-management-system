package com.toydbbackend.springbootserver.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.toydbbackend.springbootserver.model.Borrow;
import com.toydbbackend.springbootserver.repository.BorrowRepository;
import com.toydbbackend.springbootserver.repository.CardRepository;

@Controller
@RequestMapping(path = "/borrow")

public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;
    private CardRepository cardRepository;

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
        log.info("null cardID");
        List<Borrow> borrows = borrowRepository.findAll();
        return ResponseEntity.ok(borrows);
    }
}
