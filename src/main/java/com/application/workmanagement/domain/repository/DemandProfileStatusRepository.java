package com.application.workmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.workmanagement.domain.model.DemandProfileStatus;
public interface DemandProfileStatusRepository extends JpaRepository<DemandProfileStatus,Integer>{

	@Query("SELECT dps FROM DemandProfileStatus dps WHERE dps.demandid = :demandId AND dps.profileid = :profileId")
    DemandProfileStatus findByDemandIdAndProfileId(@Param("demandId") int demandId, @Param("profileId") long profileId);

	
	 List<DemandProfileStatus> findByDemandidAndStatus(int demandId, String status);

}
