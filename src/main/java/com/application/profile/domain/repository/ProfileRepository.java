package com.application.profile.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.profile.domain.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	List<Profile> findAllByPrimarySkill(String skill);

	List<Profile> findAllByAvailability(String availability);

	List<Profile> findAllByLocation(String location);

	List<Profile> findAllByName(String search);

	List<Profile> findAllByProposedBy(String search);

	List<Profile> findAllBySource(String search);

}
