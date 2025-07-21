package com.example.tna_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.entity.DayoffRequest;
import com.example.tna_app.repository.ChangeRequestRepository;
import com.example.tna_app.repository.DayoffRequestRepository;

@Service
public class AdminRequestService {

	@Autowired
	ChangeRequestRepository repository;
	
	@Autowired
	DayoffRequestRepository dayoffRepository;
		
	public List<ChangeRequest> findAll() {
		List<ChangeRequest> list = repository.findAll();	
		return list;
	} 
	
	public ChangeRequest findById(Integer id) {
		ChangeRequest cr = repository.findById(id).get();
		return cr;
	}
	
//	public List<ChangeRequest> findAllRequestWithProfile() {
//		List<ChangeRequest> list = repository.findAllWithProfile();
//		return list; 
//	}
	public DayoffRequest findDayoffRequestById(Integer id) {
		DayoffRequest dr = dayoffRepository.findById(id).get();
		return dr;
	}
	
	public List<DayoffRequest> findAllDayoffRequests() {
		List<DayoffRequest> list = dayoffRepository.findAll();	
		return list;
	} 
}