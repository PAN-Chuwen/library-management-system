package com.toydbbackend.springbootserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toydbbackend.springbootserver.model.Borrow;

// The Long type parameter is the type of the entity ID.
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findByCardCardID(String cardID);
}
