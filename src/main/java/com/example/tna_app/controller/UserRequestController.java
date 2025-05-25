package com.example.tna_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRequestController {
	
	// @Autowired
	// UserTimesheetService service;
	
	@GetMapping("/user/request/dayoff")
	public String showDayoffRequestForm() {
		return "user/dayoff-request-form";
	}
	
	@GetMapping("/user/request/change")
	public String showChangeRequestForm() {
		return "user/change-request-form";
	}
	
}
