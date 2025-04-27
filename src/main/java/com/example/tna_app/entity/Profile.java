package com.example.tna_app.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="profile")
public class Profile {
	
	@Id
	@Column(name="account_id", nullable=false)
	private Integer accountId;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="age")
	private int age;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="address")
	private String address;

	@Column(name="phone_number")
	private String phone;
	
	@Column(name="joined_date", nullable=false)
	private LocalDate joinedDate;

	@Column(name="paid_dayoff", nullable=false)
	private Integer paidDayoff;

	@Column(name="sub_dayoff", nullable=false)
	private Integer subDayoff;
	
	@OneToOne
    @JoinColumn(name="account_id", referencedColumnName = "id")
    private Account account;
	
}
