package com.example.tna_app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ChangeRequestWithProfileDTO {
    
    // change_request テーブル
    private Integer id;
    private LocalDate workingDay;
    private Integer accountId;
    private String workingStatus;
    private String reason;
    private boolean applyFlag;

    // profile テーブル
	private String name;
//	private LocalDate joinedDate;
//	private Integer paidDayoff; // 有給休暇の数
//	private Integer subDayoff; // 振替休日数
}
