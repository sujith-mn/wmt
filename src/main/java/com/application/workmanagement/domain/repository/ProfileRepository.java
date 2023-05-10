package com.application.workmanagement.domain.repository;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	/*---------------------- SEARCH------------------------ */
	
	/* Search with all fields */
	@Query(value="select * from profiles where availability =:availability and location=:location and name LIKE :name% and primary_skill=:primary_skill and  proposed_by=:proposed_by and source=:source ",nativeQuery=true)
	List<Profiles> getByAllFields(
			@Param("availability") String availability,
			@Param("location") String location,
			@Param("name") String name,
			@Param("primary_skill") String primary_skill,
			@Param("proposed_by") String proposed_by,
			@Param("source") String source
			);
	
	/* Search with Available fields */
	@Query(value="select * from profiles where availability =:availability ",nativeQuery=true)
	List<Profiles> getByAvailableFields(
			@Param("availability") String availability
			);
	
	/* Search with Location fields */
	@Query(value="select * from profiles where location=:location",nativeQuery=true)
	List<Profiles> getByLocationFields(
			
			@Param("location") String location
			);
	
	/* Search with name fields */
	@Query(value="select * from profiles where  name LIKE :name% ",nativeQuery=true)
	List<Profiles> getByNameFields(
			@Param("name") String name
			);
	
	/* Search with primary skill fields */
	@Query(value="select * from profiles where primary_skill=:primary_skill",nativeQuery=true)
	List<Profiles> getByPrimarySkillFields(
			
			@Param("primary_skill") String primary_skill
			);
	
	/* Search with proposed by fields */
	@Query(value="select * from profiles where proposed_by=:proposed_by ",nativeQuery=true)
	List<Profiles> getByProposedFields(
			
			@Param("proposed_by") String proposed_by
			);

	/* Search with source fields */
	@Query(value="select * from profiles where source=:source ",nativeQuery=true)
	List<Profiles> getBySourceFields(
			
			@Param("source") String source
			);
	
	/* Search with AvailabilityAndLocation fields */
	@Query(value="select * from profiles where availability =:availability and location=:location ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndLocationFields(
			@Param("availability") String availability,
			@Param("location") String location
			
			);
	
	/* Search with AvailabilityAndPrimarySkill fields */
	@Query(value="select * from profiles where availability =:availability and primary_skill=:primary_skill ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndPrimarySkillFields(
			@Param("availability") String availability,
			@Param("primary_skill") String primary_skill
			);
	
	/* Search with AvailabilityAndProposed fields */
	@Query(value="select * from profiles where availability =:availability and proposed_by=:proposed_by",nativeQuery=true)
	List<Profiles> getByAvailabilityAndProposedByFields(
			@Param("availability") String availability,
			
			@Param("proposed_by") String proposed_by
			);
	
	/* Search with AvailabilityAndSource fields */
	@Query(value="select * from profiles where availability =:availability and source=:source ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndSourceFields(
			@Param("availability") String availability,
			
			@Param("source") String source
			);
	
	/* Search with LocationAndSkill fields */
	@Query(value="select * from profiles location=:location and primary_skill=:primary_skill ",nativeQuery=true)
	List<Profiles> getByLocationAndSkillFields(
			
			@Param("location") String location,
			
			@Param("primary_skill") String primary_skill
			);
	
	/* Search with LocationAndProposedBy fields */
	@Query(value="select * from profiles where location=:location and proposed_by=:proposed_by ",nativeQuery=true)
	List<Profiles> getByLocationAndProposedByFields(
			@Param("location") String location,
			@Param("proposed_by") String proposed_by
			);
	
	/* Search with LocationAndSource fields */
	@Query(value="select * from profiles where location=:location and source=:source ",nativeQuery=true)
	List<Profiles> getByLocationAndSourceFields(
			@Param("location") String location,
			
			@Param("source") String source
			);
	
	/* Search with PrimarySkillAndProposedBy fields */
	@Query(value="select * from profiles where primary_skill=:primary_skill and  proposed_by=:proposed_by ",nativeQuery=true)
	List<Profiles> getByPrimarySkillAndProposedByFields(

			
			@Param("primary_skill") String primary_skill,
			@Param("proposed_by") String proposed_by
			);
	
	/* Search with Primary Skill and source fields */
	@Query(value="select * from profiles where primary_skill=:primary_skill  and source=:source ",nativeQuery=true)
	List<Profiles> getByPrimarySkillAndSourceFields(
			
			@Param("primary_skill") String primary_skill,
			@Param("source") String source
			);
	/* Search with name and primary skill fields */
	@Query(value="select * from profiles where  name LIKE :name% and primary_skill=:primary_skill ",nativeQuery=true)
	List<Profiles> getByNameAndSkillFields(
			
			@Param("name") String name,
			@Param("primary_skill") String primary_skill
			);
	
	/* Search with Availability Location Skill fields */
	@Query(value="select * from profiles where availability =:availability and location=:location  and primary_skill=:primary_skill ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndLocationAndSkillFields(
			@Param("availability") String availability,
			@Param("location") String location,
			
			@Param("primary_skill") String primary_skill
			
			);
	/* Search with Avail location name skill proposed fields */
	@Query(value="select * from profiles where availability =:availability and name LIKE :name% and primary_skill=:primary_skill",nativeQuery=true)
	List<Profiles> getByAvailableLocationNameSkillProposedFields(
			@Param("availability") String availability,
			
			@Param("name") String name,
			@Param("primary_skill") String primary_skill
			);
	
	/* Search with Availability location proposed by fields */
	@Query(value="select * from profiles where availability =:availability and location=:location and  proposed_by=:proposed_by ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndLocationAndProposedFields(
			@Param("availability") String availability,
			@Param("location") String location,
			@Param("proposed_by") String proposed_by
			);
	
	/* Search with availability location source fields */
	@Query(value="select * from profiles where availability =:availability and location=:location  and source=:source ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndLocationAndSourceFields(
			@Param("availability") String availability,
			@Param("location") String location,
			
			@Param("source") String source
			);
	
	
	/* Search with availability location skill proposed fields */
	@Query(value="select * from profiles where availability =:availability and location=:location  and primary_skill=:primary_skill and  proposed_by=:proposed_by  ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndLocationAndSkillAndProposedFields(
			@Param("availability") String availability,
			@Param("location") String location,
			
			@Param("primary_skill") String primary_skill,
			@Param("proposed_by") String proposed_by
			);
	
	/* Search with availability location skill source fields */
	@Query(value="select * from profiles where availability =:availability and location=:location and primary_skill=:primary_skill  and source=:source ",nativeQuery=true)
	List<Profiles> getByAvailabilityAndLocationAndSkillAndSourceFields(
			@Param("availability") String availability,
			@Param("location") String location,
			@Param("primary_skill") String primary_skill,
			@Param("source") String source
			);
	 
	
	/* Search with Avail location name skill proposed fields */
	@Query(value="select * from profiles where availability =:availability and location=:location and name LIKE :name% and primary_skill=:primary_skill and  proposed_by=:proposed_by  ",nativeQuery=true)
	List<Profiles> getByAvailableLocationNameSkillProposedFields(
			@Param("availability") String availability,
			@Param("location") String location,
			@Param("name") String name,
			@Param("primary_skill") String primary_skill,
			@Param("proposed_by") String proposed_by
			);
	
	/* Search with Avail location name skill source fields */
	@Query(value="select * from profiles where availability =:availability and location=:location and name LIKE :name% and primary_skill=:primary_skill and source=:source ",nativeQuery=true)
	List<Profiles> getByAvailableLocationNameSkillSourceFields(
			@Param("availability") String availability,
			@Param("location") String location,
			@Param("name") String name,
			@Param("primary_skill") String primary_skill,
			@Param("source") String source
			);
	
	
}
