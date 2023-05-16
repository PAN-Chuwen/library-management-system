package com.toydbbackend.springbootserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toydbbackend.springbootserver.model.Card;

public interface CardRepository extends JpaRepository<Card, String>{
    
}
