package com.example.tna_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.entity.DayoffRequest;
import com.example.tna_app.repository.ChangeRequestRepository;
import com.example.tna_app.repository.DayoffRequestRepository;

import jakarta.transaction.Transactional;

@Service
public class UserRequestService {

	@Autowired
	ChangeRequestRepository repository;
	
	@Autowired
	DayoffRequestRepository dayoffRepository;

	@Transactional
	public void addChangeRequest(ChangeRequest req) {
		repository.save(req);
	}

	@Transactional
	public void addDayoffRequest(DayoffRequest req) {
		dayoffRepository.save(req);
	}

}
