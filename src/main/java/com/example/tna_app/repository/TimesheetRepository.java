package com.example.tna_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.Timesheet;
import com.example.tna_app.entity.TimesheetPK;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet,  TimesheetPK> {
	
	@Query("SELECT t FROM Timesheet t "
		       + "WHERE t.id.accountId = :id "
		       + "AND FUNCTION('YEAR', t.id.workingDay) = :year "
		       + "AND FUNCTION('MONTH', t.id.workingDay) = :month "
		       + "ORDER BY t.id.workingDay asc")
    List<Timesheet> findTimesheetsByYearAndMonth(
		        @Param("id") int id,
		        @Param("year") int year,
		        @Param("month") int month
	);
	
	@Query("SELECT DISTINCT FUNCTION('YEAR', t.id.workingDay), FUNCTION('MONTH', t.id.workingDay) "
		       + "FROM Timesheet t WHERE t.id.accountId = :id ORDER BY 1, 2")
	List<Object[]> findRegisteredYearMonths(@Param("id") Integer id);

}