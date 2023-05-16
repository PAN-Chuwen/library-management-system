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

    public Book() {
    }

    public Book(Book b) {
        this.bookID = b.bookID;
        this.title = b.title;
        this.author = b.author;
        this.publisher = b.publisher;
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
}
