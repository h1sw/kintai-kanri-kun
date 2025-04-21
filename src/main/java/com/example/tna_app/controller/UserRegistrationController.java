package com.example.tna_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.tna_app.dto.RegistrationForm;
import com.example.tna_app.entity.Account;
import com.example.tna_app.repository.AccountRepository;


@Controller
@RequestMapping("/admin")
@SessionAttributes("userForm")
public class UserRegistrationController {
	
	@Autowired
	AccountRepository repository;
	
	/* 
	 * 新規アカウント登録フォームを表示
	 */
	@GetMapping("/add-account")
	public String showAddAccountForm(Model model, @ModelAttribute("userForm") RegistrationForm regForm ) {
		
		return "add-account-form";
	}
	
	/*
	 * 新規ユーザー登録を実行する
	 */
	@PostMapping("/add-user")
	public String registerUser(Model model, @ModelAttribute("formModel") Account account ) {
		repository.saveAndFlush(account);
		return "redirect:/home";
	}
}

