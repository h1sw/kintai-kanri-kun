package com.example.tna_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.entity.DayoffRequest;
import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.service.AdminRequestService;
import com.example.tna_app.service.UserTimesheetService;

@Controller
public class AdminRequestController {

	@Autowired
	AdminRequestService requestService;
	
	@Autowired
	UserTimesheetService timesheetService;
	
	@GetMapping("/admin/list-change-request")
	public String showListOfChangeRequest(Model model) {
		
		List<ChangeRequest> list = requestService.findAll();
		model.addAttribute("data", list);
		
		return "/admin/list-change-request";
	}
	
	@GetMapping("/admin/detail-change-request")
	public String showChangeRequestDetail(Model model,
			@RequestParam("request_id") Integer requestId) {
		ChangeRequest cr = requestService.findById(requestId);
		model.addAttribute("form", cr);
		
		return "/admin/detail-change-request";
	}
	
	@PostMapping("/admin/accept-change-request")
	public String acceptChangeRequest(Model model,
			@ModelAttribute("form") ChangeRequest form) {
		//申請を取得する
		ChangeRequest cr = requestService.findById(form.getId());		
		//ユーザーのタイムシートを更新する
		Timesheet ts = timesheetService.getOneTimesheet(cr.getAccount().getId(), cr.getWorkingDay());
	    ts.setWorkingStatus(cr.getNewWorkingStatus());
	    //リクエストを受理済みにする
	    cr.setApplyFlag(true);
	    timesheetService.saveOne(ts);
		
		return "redirect:/admin/success-accept-request";
	}
	
	@GetMapping("/admin/list-dayoff-request")
	public String showListOfDayoffRequest(Model model) {
		
		List<DayoffRequest> list = requestService.findAllDayoffRequests();
		model.addAttribute("data", list);
		
		return "/admin/list-dayoff-request";
	}	
	
	@GetMapping("/admin/detail-dayoff-request")
	public String showDayoffRequestDetail(Model model,
			@RequestParam("request_id") Integer requestId) {
		DayoffRequest dr = requestService.findDayoffRequestById(requestId);
		model.addAttribute("form", dr);
		
		return "/admin/detail-change-request";
	}
	
	@GetMapping("/admin/success-accept-request")
	public String showSuccessScreen() {
			
		return "/admin/success-accept-request";
	}
}