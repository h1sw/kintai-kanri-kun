package com.example.tna_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tna_app.entity.ChangeRequest;

@Repository
public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Integer>{
	
	@Query("SELECT "
		     + " cr.id, cr.workingDay, cr.workingStatus, cr.reason, cr.applyFlag, cr.account.profile.name "
		     + " FROM ChangeRequest cr")
	public List<ChangeRequest> findAllWithProfile();
}
