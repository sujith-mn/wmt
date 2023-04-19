package com.application.workmanagement.domain.repository;

import java.util.Date;
import java.util.List;

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

//	@Query(value="select * d from demand "
//			+ "where d.skill =:skill and"
//			+ " d.status =:status and"
//			+ " d.created between :startDate and :endDate",
//			nativeQuery = true)
//	List getAdvanceSearch(@Param("skill") String skill,
//			@Param("status") String status,@Param("startDate") Date startDate ,
//			@Param("endDate") Date endDate);
	
	
//	All fields
	@Query(value="select * from demand  where skill=:skill and status=:status and"
			+" demand.created between :startDate and :endDate ",nativeQuery = true)
		List<Demand> getByAllFields(@Param("skill") String skill,@Param("status") String status ,@Param("startDate") String startDate ,
				@Param("endDate") String endDate);
			
// Date 
	@Query(value="select * from demand  where "
			+" demand.created between :startDate and :endDate ",nativeQuery = true)
		List<Demand> getByDateField(@Param("startDate") String startDate ,
				@Param("endDate") String endDate);
	
//	skill
	@Query(value="select * from demand  where skill=:skill "
			,nativeQuery = true)
		List<Demand> getBySkillField(@Param("skill") String skill);
	
//	status
	@Query(value="select * from demand  where status=:status  ",nativeQuery = true)
		List<Demand> getByStatusField(@Param("status") String status);
	
}
