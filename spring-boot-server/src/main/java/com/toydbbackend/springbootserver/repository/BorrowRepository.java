package com.toydbbackend.springbootserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toydbbackend.springbootserver.model.Borrow;

// The Long type parameter is the type of the entity ID.
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findByCardCardID(String cardID);
    List<Borrow> findByBookBookID(String bookID);

    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.card.cardID = :cardID AND b.returnDate is NULL")
    int countByCardCardIDAndNotReturned(@Param("cardID") String cardID);


    @Query("SELECT returnDate FROM Borrow b WHERE b.card.cardID = :cardID AND b.returnDate is NOT NULL ORDER BY b.returnDate DESC LIMIT 5")
    List<Borrow> findByCardCardIdReturnRecords(String cardID);

    @Query("SELECT b FROM Borrow b WHERE b.card.cardID = :cardID AND b.book.bookID = :bookID AND b.returnDate is NULL")
    List<Borrow> findByCardCardIDAndBookBookIDAndNotReturned(@Param("cardID") String cardID, @Param("bookID") String bookID);
}
