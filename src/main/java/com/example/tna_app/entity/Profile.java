package com.example.tna_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="profile")
public class Profile {
	
	@Id
	@Column(name="account_id", nullable=false)
	private Integer account_id;
	
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
	private String joined_date;

	@Column(name="paid_dayoff", nullable=false)
	private Integer paid_dayoff;

	@Column(name="sub_dayoff", nullable=false)
	private Integer sub_dayoff;
	
	@OneToOne
	@MapsId
    @JoinColumn(name="account_id", referencedColumnName = "id")
    private Account account;
	
}
