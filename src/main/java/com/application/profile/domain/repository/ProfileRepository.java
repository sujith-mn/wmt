package com.application.profile.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.profile.domain.model.Profiles;

@Repository
public interface ProfileRepository extends JpaRepository<Profiles, Long> {

	List<Profiles> findAllByPrimarySkill(String skill);

	List<Profiles> findAllByAvailability(String availability);

	List<Profiles> findAllByLocation(String location);

	List<Profiles> findAllByName(String search);

	List<Profiles> findAllByProposedBy(String search);

	List<Profiles> findAllBySource(String search);

}
