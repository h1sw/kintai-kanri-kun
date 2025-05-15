package com.example.tna_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tna_app.dto.RegistrationForm;
import com.example.tna_app.service.AccountService;


@Controller
@RequestMapping("/admin")
public class UserRegistrationController {
	
	@Autowired
	AccountService service; 
	
	@ModelAttribute("form")
	public RegistrationForm initForm() {
		return new RegistrationForm();
	}
	
	/* 
	 * 新規アカウント登録フォームを表示
	 */
	@GetMapping("/add-user")
	@PreAuthorize("hasRole('ADMIN')")
	public String showAddAccountForm(@ModelAttribute("form") RegistrationForm regForm) {
		
		return "admin/add-user-form";
	}
	
	/*
	 * アカウント登録を実行
	 */
	@PostMapping("/add-user")
	@PreAuthorize("hasRole('ADMIN')")
	public String registerAccount(@ModelAttribute("form") RegistrationForm regForm) {
		
		service.registerUser(regForm);
		return "redirect:/admin/success-add-user";
	}
	
	/*
	 * 確認画面を表示
	 */
	@GetMapping("/success-add-user")
	@PreAuthorize("hasRole('ADMIN')")
	public String showSuccessRegistration() {
		
		return "admin/success-add-user";
		
	}
}

