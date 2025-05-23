package com.example.tna_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}