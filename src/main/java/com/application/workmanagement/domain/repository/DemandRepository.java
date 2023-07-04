package com.application.workmanagement.domain.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.workmanagement.domain.model.Demand;
import com.application.workmanagement.service.rest.v1.model.DemandDto;


public interface DemandRepository extends JpaRepository<Demand , Integer>{

	List<DemandDto> findByManagerIgnoreCase(String manager, Pageable paging);

	List<DemandDto> findByManager(String manager, Pageable paging);

	List<DemandDto> findBySkillContaining(String manager, Pageable paging);
	
//	All fields
	@Query(value="select * from demand  where skill=:skill and status=:status and"
			+" demand.created between :startDate and :endDate ",nativeQuery = true)
		List<Demand> getByAllFields(@Param("skill") String skill,@Param("status") String status ,@Param("startDate") String startDate ,
				@Param("endDate") String endDate);
			
// Date 
	@Query(value="select * from demand  where "
			+" demand.created between :startDate and :endDate ",nativeQuery = true)
		List<Demand> getByDateField(
				@Param("startDate") String startDate ,
				@Param("endDate") String endDate
				);
	
//	skill
	@Query(value="select * from demand  where skill=:skill "
			,nativeQuery = true)
		List<Demand> getBySkillField(
				@Param("skill") String skill
				);
	
//	status
	@Query(value="select * from demand  where status=:status  ",nativeQuery = true)
		List<Demand> getByStatusField(
				@Param("status") String status
				);
	
//Skill and Status
	@Query(value="select * from demand  where skill=:skill and status=:status ",nativeQuery = true)
		List<Demand> getBySkillAndStatusFields(
				@Param("skill") String skill,
				@Param("status") String status 
				);

	
//Status and Dates
	@Query(value="select * from demand  where status=:status and"
			+" demand.created between :startDate and :endDate ",nativeQuery = true)
		List<Demand> getByStatusAndDateFields(
				@Param("status") String status ,
				@Param("startDate") String startDate ,
				@Param("endDate") String endDate
				);
	
//Skill and Dates
	@Query(value="select * from demand  where skill=:skill  and"
			+" demand.created between :startDate and :endDate ",nativeQuery = true)
		List<Demand> getDemandsBySkillAndDateFields(
				@Param("skill") String skill ,
				@Param("startDate") String startDate ,
				@Param("endDate") String endDate);
	
// first date 
	@Query(value="select * from demand  where demand.created >=:startDate",nativeQuery = true)
		List<Demand> getByStartDateFields(
				@Param("startDate") String startDate 
				);
	
//first date ,status , skill
	@Query(value="select * from demand  where skill=:skill and status=:status and"
			+" demand.created >=:startDate",nativeQuery = true)
		List<Demand> getBySkillStatusStartDateFields(
				@Param("skill") String skill,
				@Param("status") String status ,
				@Param("startDate") String startDate
				);
	
//first date and status 
	@Query(value="select * from demand  where status=:status and demand.created >=:startDate",nativeQuery = true)
		List<Demand> getByStatusStartDateFields(
				@Param("status") String status ,
				@Param("startDate") String startDate 
				);
	
//first date and skill	
	@Query(value="select * from demand  where skill=:skill and demand.created >=:startDate ",nativeQuery = true)
		List<Demand> getBySkillStartDateFields(
				@Param("skill") String skill,
				@Param("startDate") String startDate
				);
	
	
	
}
