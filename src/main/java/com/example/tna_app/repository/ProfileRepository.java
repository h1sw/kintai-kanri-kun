package com.example.tna_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{

}
