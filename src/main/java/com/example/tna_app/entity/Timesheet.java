package com.example.tna_app.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="timesheet")
public class Timesheet {
	
//    @Id
//    @Column(name="account_id", nullable=false)
//    private Integer accountId;
//    
//    @Id
//    @Column(name="working_day", nullable=false)	
//    private LocalDate workingDay;

	@EmbeddedId
	private TimesheetPK id;
	
    @Column(name="working_status", nullable=false)	
    private String workingStatus;
    
    @Column(name="attend_time")
    private LocalTime attendTime;
    
    @Column(name="leave_time")
    private LocalTime leaveTime;
    
    @Column(name="round_attend_time")
    private LocalTime roundAttendTime;
    
    @Column(name="round_leave_time")
    private LocalTime roundLeaveTime;

    @Column(name="breaktime")
    private LocalTime breaktime;
    
    @Column(name="overtime")
    private LocalTime overtime;

    @Column(name="stepout")
    private LocalTime stepout;

    @Column(name="finalized_flag")
    private boolean finalizedFlag;

    @Column(name="edited_flag")
    private boolean editedFlag;

    @Column(name="requested_flag")
    private boolean requestedFlag;
    
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name="account_id", referencedColumnName="id")
    private Account account;
    
    
}


/*   account_id INTEGER NOT NULL,
working_day DATE,
working_status VARCHAR(20), -- 例: '出勤', '休日'...
attend_time TIME, --実際の出勤時間
leave_time TIME, --実際の退勤時間
break TIME, --休憩時間
round_attend_time TIME, --五捨六入した出勤時間
round_leave_time TIME, --五捨六入した退勤時間
overtime TIME,  --残業時間.出退勤から自動計算
stepout TIME, --中抜けに要した時間.自己申告
finalized_flag TINYINT(1) NOT NULL, --確定フラグ。管理者への申請でtrue
edited_flag TINYINT(1) NOT NULL, --編集済みフラグ。編集後にtrue
requested_flag TINYINT(1) NOT NULL, --申請フラグ。申請中にtrue
PRIMARY KEY (account_id, working_day),
FOREIGN KEY (account_id) REFERENCES account(id)
);
*/