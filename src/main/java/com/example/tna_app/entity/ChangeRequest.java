package com.example.tna_app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="change_request")
public class ChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "working_day", nullable = false)
    private LocalDate workingDay;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "working_status")
    private String workingStatus;

    @Column(name = "attend_time")
    private LocalTime attendTime;

    @Column(name = "leave_time")
    private LocalTime leaveTime;

    @Column(name = "reason")
    private String reason;

    @Column(name = "apply_flag")
    private Boolean applyFlag = false;
}

//-- 変更申請テーブル
//CREATE TABLE IF NOT EXISTS change_request (
//   　id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
//    working_day DATE NOT NULL, 
//    account_id INTEGER NOT NULL,
//    working_status VARCHAR(20),
//    attend_time TIME,
//    leave_time TIME,
//    reason VARCHAR(255),
//    apply_flag TINYINT(1),
//    FOREIGN KEY (account_id) REFERENCES account(id)
//);