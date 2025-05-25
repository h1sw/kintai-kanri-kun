package com.example.tna_app.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.repository.TimesheetRepository;

@Service
public class UserTimesheetService {

	@Autowired
	TimesheetRepository repository;
	
	public List<Timesheet> getTimesheets(int id, int year, int month) {
		List<Timesheet> list = repository.findTimesheetsByYearAndMonth(id, year, month);
		return list;
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
	
	public List<LocalDate> getAllWorkingDays(int year, int month) {
		List<LocalDate> list = new ArrayList<>();
		YearMonth ym = YearMonth.of(year, month);
		for(int day = 1; day <= ym.lengthOfMonth(); day++ ) {
			list.add(LocalDate.of(year, month, day));
		}
		
		return list;
	}
	
	public void saveAll(List<Timesheet> list) {
	    repository.saveAll(list);
	}
}
