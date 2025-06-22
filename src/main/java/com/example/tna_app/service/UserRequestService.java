package com.example.tna_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.repository.ChangeRequestRepository;

import jakarta.transaction.Transactional;

@Service
public class UserRequestService {

	@Autowired
	ChangeRequestRepository repository;
	
	@Transactional
	public void addChangeRequest(ChangeRequest req) {
		repository.save(req);
	}

}
