package com.example.tna_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.repository.ChangeRequestRepository;

@Service
public class AdminRequestService {

	@Autowired
	ChangeRequestRepository repository;
		
	public List<ChangeRequest> findAll() {
		List<ChangeRequest> list = repository.findAll();	
		return list;
	} 
	
	public List<ChangeRequest> findAllRequestWithProfile() {
		List<ChangeRequest> list = repository.findAllWithProfile();
		return list; 
	}

}