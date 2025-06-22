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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tna_app.entity.Account;
import com.example.tna_app.entity.ChangeRequest;
import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.entity.TimesheetPK;
import com.example.tna_app.service.AccountService;
import com.example.tna_app.service.UserRequestService;
import com.example.tna_app.service.UserTimesheetService;

@Controller
public class UserTimesheetController {
	
	@Autowired
	UserTimesheetService timesheetService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	UserRequestService userRequestService;
	
	@GetMapping("/user/timesheet")
	public String showTimesheet(
			@RequestParam(value="year", required=false) Integer year,
	        @RequestParam(value="month", required=false) Integer month,
	        Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer accountId = Integer.parseInt(auth.getName());
	    
		LocalDate today = LocalDate.now();
		Integer currentMonth = today.getMonth().getValue();
		Integer currentYear = today.getYear();
		
		// パラメータがnullなら現在の年月をセットする
		if (year == null || month == null) {
			year = currentYear;
			month = currentMonth;
		}
		
		List<YearMonth> availableList = timesheetService.getAvailableYearMonths(accountId);
	    YearMonth selected = YearMonth.of(year, month);
	    boolean isExist = availableList.contains(selected);

	    if (isExist) {
	        List<Timesheet> timesheet = timesheetService.getTimesheets(accountId, year, month);
	        model.addAttribute("timesheet", timesheet);
	    } else if (selected.equals(YearMonth.of(currentYear, currentMonth).plusMonths(1)) ) {
	    	model.addAttribute("msg", "来月の勤務表はありません。");
	    	model.addAttribute("creatable", true);
	    } else if (selected.equals(YearMonth.of(currentYear, currentMonth))){
	    	model.addAttribute("msg", "今月の勤務表はありません。");
	    	model.addAttribute("creatable", true);
	    } else {
	    	model.addAttribute("msg", "該当する月の勤務表はありません。翌月の勤務表のみ作成できます。");
	    }
	    
	    model.addAttribute("year", year);
	    model.addAttribute("month", month);
	    model.addAttribute("availableMonths", availableList);
	    model.addAttribute("isExist", isExist);
		
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
		
		List<String> workingDays = timesheetService.getAllFormattedWorkingDays(year, month);
		
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
	    Account account = accountService.findOneAccount(accountId);
	    
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
	        ts.setAccount(account);
	        timesheets.add(ts);
	    }

	    timesheetService.saveAll(timesheets);
	    return "redirect:/user/timesheet?year=" + year + "&month=" + month;
	}

	// 当日の勤怠入力画面を開く
	@GetMapping("/user/clockin")
	public String showClockInForm (Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Integer accountId = Integer.parseInt(auth.getName());
	    Account account = accountService.findOneAccount(accountId);
	    LocalDate today = LocalDate.now();
	    Integer year = today.getYear();
	    Integer month = today.getMonthValue();
	    Integer day = today.getDayOfMonth();
	    
	    model.addAttribute("year", year);
	    model.addAttribute("month", month);
	    model.addAttribute("day", day);
	    
	    List<YearMonth> availableList = timesheetService.getAvailableYearMonths(accountId);
	    YearMonth selected = YearMonth.of(year, month);
	    boolean isExist = availableList.contains(selected);
	    
	    if (isExist) {
	    	Timesheet ts = timesheetService.getOneTimesheet(account.getId(), today);
	        model.addAttribute("form", ts);
	        return "/user/form-clocking-in";
	    } else {
	    	return "redirect:/user/timesheet?year=" + year + "&month=" + month;
	    }
	}
	
	@PostMapping("/user/clockin")
	public String clockIn (Model model, @ModelAttribute("form") Timesheet ts) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Integer accountId = Integer.parseInt(auth.getName());
	    Account account = accountService.findOneAccount(accountId);
	    TimesheetPK pk = new TimesheetPK();
	    pk.setAccountId(accountId);
	    pk.setWorkingDay(ts.getId().getWorkingDay());
	    ts.setId(pk);
	    ts.setAccount(account);
	    ts.setRoundAttendTime(timesheetService.calcRoundedTime(ts.getAttendTime()));
	    ts.setRoundLeaveTime(timesheetService.calcRoundedTime(ts.getLeaveTime()));
	    ts.setOvertime(timesheetService.calcOvertime(ts.getRoundAttendTime(), ts.getRoundLeaveTime(), ts.getBreaktime(), ts.getStepout()));
	    
        timesheetService.saveOne(ts);
        return "redirect:/home";
	}

	@GetMapping("/user/change-request")
	public String showChangeRequestForm(
			Model model,
			@RequestParam("year") int year,
			@RequestParam("month") int month,
			@RequestParam("day") int day
			) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Integer accountId = Integer.parseInt(auth.getName());
//	    Account account = accountService.findOneAccount(accountId);
	    
	    LocalDate date = LocalDate.of(year, month, day);
	    Timesheet ts = timesheetService.getOneTimesheet(accountId, date);
	    ChangeRequest req = new ChangeRequest();
	    req.setAccountId(accountId);
	    req.setWorkingDay(date);
	    req.setWorkingStatus(ts.getWorkingStatus());

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

		userRequestService.addChangeRequest(req);
		
		return "redirect:/user/timesheet";
	}
}





















