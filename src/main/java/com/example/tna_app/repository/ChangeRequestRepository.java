package com.example.tna_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.ChangeRequest;

@Repository
public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Integer>{

	public List<ChangeRequest> findAll();
	public Optional<ChangeRequest> findById(Integer id);
}
