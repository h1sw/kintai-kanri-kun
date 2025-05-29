package com.example.tna_app.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.entity.TimesheetPK;
import com.example.tna_app.service.UserTimesheetService;

@Controller
public class UserTimesheetController {
	
	@Autowired
	UserTimesheetService service;
	
	@GetMapping("/user/timesheet")
	public String showTimesheet(
			@RequestParam(value="year", required=false) Integer year,
	        @RequestParam(value="month", required=false) Integer month,
	        Model model) {
		
		if (year == null || month == null) {
			LocalDate today = LocalDate.now();
			month = today.getMonth().getValue();
			year = today.getYear();
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer accountId = Integer.parseInt(auth.getName());
	    
		List<YearMonth> availableList = service.getAvailableYearMonths(accountId);
	    YearMonth selected = YearMonth.of(year, month);
	    boolean isAvailable = availableList.contains(selected);

	    if (isAvailable) {
	        List<Timesheet> timesheet = service.getTimesheets(accountId, year, month);
	        model.addAttribute("timesheet", timesheet);
	    }

	    model.addAttribute("year", year);
	    model.addAttribute("month", month);
	    model.addAttribute("availableMonths", availableList);
	    model.addAttribute("isAvailable", isAvailable);
		
		return "user/timesheet";
	}

	@PostMapping("/user/timesheet")
	public String changeTimesheet(
	        @RequestParam("year") Integer year,
	        @RequestParam("month") Integer month) {
	    return "redirect:/user/timesheet?year=" + year + "&month=" + month;
	}
	
	@PostMapping("/user/timesheet/plan")
	public String planTimesheet(
			Model model,
	        @RequestParam("year") Integer year,
	        @RequestParam("month") Integer month) {
		
		List<String> workingDays = service.getAllFormattedWorkingDays(year, month);
		
	    model.addAttribute("year", year);
	    model.addAttribute("month", month);
	    model.addAttribute("days", workingDays);
	    
	    return "user/timesheet-plan-form";
	}	

	
	@PostMapping("/user/timesheet/create")
	public String savePlannedTimesheet(
	        @RequestParam("year") int year,
	        @RequestParam("month") int month,
	        @RequestParam MultiValueMap<String, String> formData
	){
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Integer accountId = Integer.parseInt(auth.getName());

	    YearMonth ym = YearMonth.of(year, month);
	    List<Timesheet> timesheets = new ArrayList<>();
	    
	    for (int day = 1; day <= ym.lengthOfMonth(); day++) {
	        LocalDate date = LocalDate.of(year, month, day);
	        String key = "status_" + date.toString();
	        String status = formData.getFirst(key);

	        if (status == null || status.isEmpty()) continue;

	        Timesheet ts = new Timesheet();
	        TimesheetPK pk = new TimesheetPK();
	        pk.setAccountId(accountId);
	        pk.setWorkingDay(date);

	        ts.setId(pk);
	        ts.setWorkingStatus(status);
	        ts.setFinalizedFlag(false);
	        ts.setEditedFlag(false);
	        ts.setRequestedFlag(false);
	        timesheets.add(ts);
	    }

	    service.saveAll(timesheets);
	    return "redirect:/user/timesheet?year=" + year + "&month=" + month;
	}

}
