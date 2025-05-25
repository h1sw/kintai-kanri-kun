package com.example.tna_app.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TimesheetPK implements Serializable{
	
    @Column(name="account_id", nullable=false)
	private Integer accountId;

    @Column(name="working_day", nullable=false)
    private LocalDate workingDay;
}
