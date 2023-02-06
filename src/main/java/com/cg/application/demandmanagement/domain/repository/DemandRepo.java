package com.cg.application.demandmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.application.demandmanagement.domain.model.Demand;


public interface DemandRepo extends JpaRepository<Demand , Integer>{

}
