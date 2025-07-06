package com.example.tna_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.service.AdminRequestService;

@Controller
public class AdminRequestController {

	@Autowired
	AdminRequestService requestService;
	
	@GetMapping("/admin/request/change")
	public String showListOfChangeRequest(Model model) {
		
		List<ChangeRequest> list = requestService.findAllRequestWithProfile();
		model.addAttribute("data", list);
		
		return "/admin/list-change-request";
	}
}