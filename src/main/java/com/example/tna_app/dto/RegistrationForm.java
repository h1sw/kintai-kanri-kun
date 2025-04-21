package com.example.tna_app.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RegistrationForm {
	
	// アカウント
    private String password;
    private String role;

    // プロファイル
    private String name;
    private Integer age;
    private String sex;
    private String address;
    private String phone;
    private Date joinedDate;
    private Integer paidDayoff;
    private Integer subDayoff;
}
