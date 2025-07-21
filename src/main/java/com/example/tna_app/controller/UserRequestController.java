package com.example.tna_app.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tna_app.entity.Account;
import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.entity.DayoffRequest;
import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.service.AccountService;
import com.example.tna_app.service.UserRequestService;
import com.example.tna_app.service.UserTimesheetService;

@Controller
public class UserRequestController {

	@Autowired
	UserRequestService service;
	@Autowired
	AccountService accountService;
	@Autowired
	UserTimesheetService timesheetService;
	
	@GetMapping("/user/change-request")
	public String showChangeRequestForm(
			Model model,
			@RequestParam("year") int year,
			@RequestParam("month") int month,
			@RequestParam("day") int day
			) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Integer accountId = Integer.parseInt(auth.getName());
	    Account account = accountService.findOneAccount(accountId);
	    
	    LocalDate date = LocalDate.of(year, month, day);
	    Timesheet ts = timesheetService.getOneTimesheet(accountId, date);
	    ChangeRequest req = new ChangeRequest();
	    req.setAccount(account);
	    req.setWorkingDay(date);
	    req.setOldWorkingStatus(ts.getWorkingStatus());

	    model.addAttribute("year", year);
	    model.addAttribute("month", month);
	    model.addAttribute("day", day);
	    model.addAttribute("form", req);
	    
		return "/user/form-change-request";
	}
	
	@PostMapping("/user/change-request")
	public String submitChangeRequest(
			Model model, 
			@ModelAttribute("form") ChangeRequest req) {

		service.addChangeRequest(req);
		
		return "redirect:/user/timesheet";
	}
	
	@GetMapping("/user/dayoff-request")
	public String showDayoffRequestFrom(
			Model model, 
			@RequestParam("year") int year,
			@RequestParam("month") int month,
			@RequestParam("day") int day
			) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Integer accountId = Integer.parseInt(auth.getName());
	    Account account = accountService.findOneAccount(accountId);
	    
	    LocalDate date = LocalDate.of(year, month, day);
	    DayoffRequest req = new DayoffRequest();
	    req.setAccount(account);
	    req.setWorkingDay(date);

	    model.addAttribute("year", year);
	    model.addAttribute("month", month);
	    model.addAttribute("day", day);
	    model.addAttribute("form", req);
	    
		return "/user/form-dayoff-request";
	}
	
	@PostMapping("/user/dayoff-request")
	public String submitDayoffRequest(
			Model model, 
			@ModelAttribute("form") DayoffRequest req) {

		service.addDayoffRequest(req);
		
		return "redirect:/user/timesheet";
	}
	
}