package com.example.tna_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.DayoffRequest;

@Repository
public interface DayoffRequestRepository extends JpaRepository<DayoffRequest, Integer>{

	public List<DayoffRequest> findAll();
	public Optional<DayoffRequest> findById(Integer id);
}
