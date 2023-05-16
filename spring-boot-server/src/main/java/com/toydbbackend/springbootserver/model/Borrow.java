package com.toydbbackend.springbootserver.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrow")
public class Borrow {
    
    // error would occur if trying to add a non-nullable column named borrowID to the borrow table, but the table is not empty
    // one possible solution is to alter the table in DB level, adding a new column to be Primary Key.
    @Id
    private Integer borrowID;

    @ManyToOne
    @JoinColumn(name = "card_no")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "book_no")
    private Book book;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public long getBorrowID() {
        return borrowID;
    }

    public Card getCard() {
        return card;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // equals and hashCode are needed when using composite primary key


}
