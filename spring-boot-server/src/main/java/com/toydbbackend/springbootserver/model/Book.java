package com.toydbbackend.springbootserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_no")
    private String bookID;

    private String title;
    private String author;
    private String publisher;
    private Integer total;
    private Integer stock;

    public Book() {
    }

    public Book(Book b) {
        this.bookID = b.bookID;
        this.title = b.title;
        this.author = b.author;
        this.publisher = b.publisher;
        this.total = b.total;
        this.stock = b.stock;
    }

    /*
     * 1. this.NULL + other.NULL: true 2. this.NULL + other.NOT NULL: true 3. this.NOT
     * NULL + other.NOT NULL: false 4. both not null, compare content
     */
    public boolean partialMatch(Book other) {
        if (other == null)
            return false;
        if (this.bookID != null && !this.bookID.equals(other.bookID))
            return false;
        if (this.title != null && other.title != null && !this.title.equals(other.title))
            return false;
        if (this.author != null && other.author != null && !this.author.equals(other.author))
            return false;
        if (this.publisher != null && other.publisher != null && !this.publisher.equals(other.publisher))
            return false;
        // Add more fields as needed
        return true;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer setStock(Integer stock) {
        return this.stock = stock;
    }
}
