package com.example.tna_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{
		
    // 名前だけで検索
    List<Profile> findByNameContaining(String name);

    // 社員IDだけで検索
    @Query("SELECT p FROM Profile p WHERE p.accountId = :id")
    List<Profile> findByAccountId(@Param("id") Integer id);

    // IDと名前
    @Query("SELECT p FROM Profile p WHERE p.accountId = :id OR p.name LIKE %:name%")
    List<Profile> findByAccountIdAndNameContaining(@Param("id") Integer id, @Param("name") String name);
}
