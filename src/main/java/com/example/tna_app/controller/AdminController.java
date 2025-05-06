package com.example.tna_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tna_app.entity.Account;
import com.example.tna_app.entity.Profile;
import com.example.tna_app.service.AccountService;

@Controller
@RequestMapping("/admin")
public class AdminController {

		@Autowired 
		AccountService service; 
		
		@GetMapping("/search")
		public String showSearchForm(Model model) {
			List<Profile> list = service.findAllProfiles();
			model.addAttribute("searchResults", list);
			return "search-form";
		}
		
		@PostMapping("/search")
		public String showResult(
				@RequestParam(required=false) Integer accountId,
				@RequestParam(required=false) String name,
				Model model) {
			
			List<Profile> list = service.findProfile(accountId, name);
			model.addAttribute("searchResults", list);
			return "search-form";
		}
		
		@GetMapping("/delete/{id}")
		public String showDeletePage(@PathVariable int id, Model model) {
			Profile profile = service.findOneProfile(id);
			
			model.addAttribute("form", profile);
			return "confirm-delete";
		}

		@PostMapping("/delete")
		public String deleteUser(@ModelAttribute("form") Profile profile) {
			service.deleteUserById(profile.getAccountId());
			return "redirect:/home";
		}

		@GetMapping("/edit/{id}")
		public String showEditPage(@PathVariable int id, Model model) {
			Profile profile = service.findOneProfile(id);
			model.addAttribute("form", profile);
			return "edit-form";
		}

		@PostMapping("/edit/save")
		public String editUser(@ModelAttribute("form") Profile profile) {
			Integer accountId = profile.getAccountId();
			Account account = service.findOneAccount(accountId);
			account.setRole(profile.getAccount().getRole());
			profile.setAccount(account);
			
			service.saveProfile(profile);
			return "redirect:/home";
		}
}
