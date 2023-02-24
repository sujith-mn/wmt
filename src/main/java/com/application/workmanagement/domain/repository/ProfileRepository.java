package com.application.workmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.workmanagement.domain.model.Profiles;


public interface ProfileRepository extends JpaRepository<Profiles, Long> {

	List<Profiles> findAllByPrimarySkill(String skill);

	List<Profiles> findAllByAvailability(String availability);

	List<Profiles> findAllByLocation(String location);

	List<Profiles> findAllByName(String search);

	List<Profiles> findAllByProposedBy(String search);

	List<Profiles> findAllBySource(String search);

	List<Profiles> findByPrimarySkillIgnoreCase(String search);

	List<Profiles> findByAvailabilityIgnoreCase(String search);

	List<Profiles> findByLocationIgnoreCase(String search);
	
	List<Profiles> findBySourceIgnoreCase(String search);
	
	List<Profiles> findByProposedByIgnoreCase(String search);
	
	List<Profiles> findByNameIgnoreCase(String search);

}
