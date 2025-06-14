package com.example.tna_app.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.entity.TimesheetPK;
import com.example.tna_app.repository.TimesheetRepository;

@Service
public class UserTimesheetService {

	@Autowired
	TimesheetRepository repository;
	
	public List<Timesheet> getTimesheets(int id, int year, int month) {
		List<Timesheet> list = repository.findTimesheetsByYearAndMonth(id, year, month);
		return list;
	}
	
	public Timesheet getOneTimesheet(int id, LocalDate date) {
		TimesheetPK pk = new TimesheetPK();
		
		pk.setAccountId(id);
		pk.setWorkingDay(date);
		
		Timesheet ts = repository.findById(pk).orElse(null);
		
		return ts;
	}
	
	public List<YearMonth> getAvailableYearMonths(int accountId) {

	    List<Object[]> list = repository.findRegisteredYearMonths(accountId);
	    List<YearMonth> result = new ArrayList<>();
	    for (Object[] arr : list) {
	        int year = (Integer) arr[0];
	        int month = (Integer) arr[1];
	        result.add(YearMonth.of(year, month));
	    }
	    return result;
	}
	
	public List<String> getAllFormattedWorkingDays(int year, int month) {
		List<String> list = new ArrayList<>();
		YearMonth ym = YearMonth.of(year, month);
		for(int day = 1; day <= ym.lengthOfMonth(); day++ ) {
			list.add(LocalDate.of(year, month, day).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		
		return list;
	}
	
	public String formatTimeHHmmIfExist(LocalTime time) {
		if (time != null) { 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			return time.format(formatter);
		} else 
			return null;
	}
	
	public LocalTime calcRoundedTime(LocalTime time) {
		LocalTime roundedTime = time;
		int mm = time.getMinute();
		int m = mm % 10;
		
		if (m <= 5 ) {
			return roundedTime.withMinute(mm-m).withSecond(0).withNano(0);
		} else {
			int roundedMin = mm + 10 - m;
			if (roundedMin >= 60) {
				return roundedTime.withHour(time.getHour() + 1).withMinute(roundedMin - 60).withSecond(0).withNano(0);
			} else {
				return roundedTime.withMinute(roundedMin).withSecond(0).withNano(0);
			}
		}
	}
	
	public LocalTime calcOvertime(LocalTime start, LocalTime end, LocalTime breaktime, LocalTime stepout) {
		int legalWorkingTime = 8;
		Duration breaktimeDuration = Duration.between(LocalTime.MIDNIGHT, breaktime);
		Duration stepoutDuration = Duration.between(LocalTime.MIDNIGHT, stepout);
		Duration duration = Duration.between(start, end).minus(breaktimeDuration).minus(stepoutDuration).minusHours(legalWorkingTime);
		if (duration.isNegative()) {
			return LocalTime.MIDNIGHT;
		} else {
		    return LocalTime.MIDNIGHT.plus(duration);
		}
	}
	
	public void saveAll(List<Timesheet> list) {
	    repository.saveAll(list);
	}
	
	public void saveOne(Timesheet timesheet) {
		repository.save(timesheet);
	}
}
