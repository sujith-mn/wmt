package com.application.workmanagement.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.application.workmanagement.domain.model.Demand;
import com.application.workmanagement.service.rest.v1.model.DemandDto;


public interface DemandRepository extends JpaRepository<Demand , Integer>{

	List<DemandDto> findByManagerIgnoreCase(String manager, Pageable paging);

	List<DemandDto> findByManager(String manager, Pageable paging);

	List<DemandDto> findBySkillContaining(String manager, Pageable paging);




}
