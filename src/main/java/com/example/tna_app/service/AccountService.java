package com.example.tna_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Transactional
	public void registerUser (RegistrationForm form) {
		Account account = new Account();
		account.setPassword(form.getPassword());
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
	    profile.setPaidDayoff(form.getPaidDayoff());
	    profile.setSubDayoff(form.getSubDayoff());
	    profileRepository.save(profile);
	}
	
	public void saveProfile(Profile profile) {
	    profileRepository.save(profile);
	}
	
	public List<Profile> findAllProfiles () {
		return profileRepository.findAll();
	}

	public Optional<Profile> findOneProfile(Integer id) {
		return profileRepository.findById(id);
	}
	
	public List<Profile> findProfile(Integer id, String name) {
		boolean hasId = id != null;
		boolean hasName = name != null && !name.isBlank();
		
		if (hasId && hasName) {
			return profileRepository.findByAccountIdAndNameContaining(id, name);
		} else if (hasId) { 
			return profileRepository.findByAccountId(id);
		} else if (hasName) {
			return profileRepository.findByNameContaining(name);
		} else {
			return profileRepository.findAll();
		}
	}
	
	@Transactional
	public void deleteUserById(Integer id) {
		profileRepository.deleteById(id);
		accountRepository.deleteById(id);
	}
}