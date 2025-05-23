package com.example.tna_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tna_app.dto.RegistrationForm;
import com.example.tna_app.entity.Account;
import com.example.tna_app.entity.Profile;
import com.example.tna_app.repository.AccountRepository;
import com.example.tna_app.repository.ProfileRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public void registerUser (RegistrationForm form) {
		Account account = new Account();
		String hashedPassword = passwordEncoder.encode(form.getPassword());
		account.setPassword(hashedPassword);
		account.setRole(form.getRole());
		accountRepository.save(account);
		
		Profile profile = new Profile();
	    profile.setAccountId(account.getId());
	    profile.setName(form.getName());
	    profile.setAge(form.getAge());
	    profile.setSex(form.getSex());
	    profile.setAddress(form.getAddress());
	    profile.setPhone(form.getPhone());
	    profile.setJoinedDate(form.getJoinedDate());
	    profile.setPaidDayoff(0);
	    profile.setSubDayoff(0);
	    profileRepository.save(profile);
	}
	
	@Transactional
	public String encryptedPasswordOf (String str) {
		return passwordEncoder.encode(str);
	}
	
	@Transactional
	public void saveProfile(Profile profile) {
	    profileRepository.save(profile);
	}
	
	@Transactional
	public void saveAccount(Account account) {
	    accountRepository.save(account);
	}	
		
	@Transactional
	public List<Profile> findAllProfiles () {
		return profileRepository.findAll();
	}

	@Transactional
	public Profile findOneProfile(Integer id) {
		return profileRepository.findById(id).get();
	}
	
	@Transactional
	public Account findOneAccount(Integer id) {
		return accountRepository.findById(id).get();
	}
	
	@Transactional
	public List<Profile> findProfile(Integer id, String name) {
		return profileRepository.searchProfiles(id, name);
	}
	
	@Transactional
	public void deleteUserById(Integer id) {
		profileRepository.deleteById(id);
		accountRepository.deleteById(id);
	}
	
	public Profile findCurrentProfile() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer current_user = Integer.parseInt(auth.getName());
		Profile profile = profileRepository.findById(current_user).get();
		return profile;
	}
}