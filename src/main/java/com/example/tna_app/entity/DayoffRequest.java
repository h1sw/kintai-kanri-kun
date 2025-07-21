package com.example.tna_app.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="dayoff_request")
public class DayoffRequest {
	
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "working_day", nullable = false)
    private LocalDate workingDay;

//    @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
//    private Integer accountId;

    @Column(name = "type")
    private String type;
    
    @Column(name = "reason")
    private String reason;

    @Column(name = "apply_flag")
    private Boolean applyFlag = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}


//-- 休暇申請テーブル
//CREATE TABLE IF NOT EXISTS leave_request (
//    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
//    working_day DATE NOT NULL, 
//    account_id INTEGER NOT NULL,
//    reason VARCHAR(255),
//    apply_flag TINYINT(1),
//    FOREIGN KEY (account_id) REFERENCES account(id)
//); 