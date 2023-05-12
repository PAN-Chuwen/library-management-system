package com.toydbbackend.springbootserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Book {
    @Id
    private String book_no;

    private String title;
    private String author;
    private String publisher;

    public String getBook_no() {
        return book_no;
    }

    public void setBook_no(String book_no) {
        this.book_no = book_no;
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

    public String getPublisher() {
        return publisher;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
