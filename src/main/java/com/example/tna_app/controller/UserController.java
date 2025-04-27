package com.example.tna_app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 一般ユーザー向けの機能のコントローラー
 */
@Controller
public class UserController {
	
	
	@GetMapping("/home")
	@PreAuthorize("isAuthenticated()")
	public String home() {
		
		return "home";
	}
	
	
}
