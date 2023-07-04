package com.application.workmanagement.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.common.exception.DemandIdNotFoundException;
import com.application.common.exception.ProfileNotFoundException;
import com.application.workmanagement.domain.model.Demand;
import com.application.workmanagement.domain.model.DemandProfileStatus;
import com.application.workmanagement.domain.model.DemandsExcel;
import com.application.workmanagement.domain.model.Profiles;
import com.application.workmanagement.domain.repository.DemandProfileStatusRepository;
import com.application.workmanagement.domain.repository.DemandRepository;
import com.application.workmanagement.domain.repository.ProfileRepository;
import com.application.workmanagement.service.rest.v1.model.DemandDto;
import com.application.workmanagement.service.rest.v1.model.ProfileDto;
import com.application.workmanagement.service.rest.v1.model.ProfilesListDto;
import com.xlm.reader.SheetReader;

@Service
public class DemandService {

	@Autowired
	private DemandRepository demandRepository;

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ModelMapper modelMapper;

	
	private final DemandProfileStatusRepository demandProfileStatusRepository;
	// create demand

	public DemandDto createDemand(DemandDto demandDto) {
		Demand demand = this.dtoToDemand(demandDto);
		Demand savedDemand = this.demandRepository.save(demand);

		return this.demandToDto(savedDemand);
	}

	// Update demand.

	public DemandDto updateDemand(DemandDto demandDto, int demandId) {
		// TODO Auto-generated method stub
		Demand demand = this.demandRepository.findById(demandId)
				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));

		// demand.setDemandId(demandDto.getDemandId());
		demand.setManager(demandDto.getManager());
		demand.setCreated(demandDto.getCreated());
		demand.setEndDate(demandDto.getEndDate());
		demand.setAgeing(demandDto.getAgeing());
		demand.setPriority(demandDto.getPriority());
		demand.setSkill(demandDto.getSkill());
		demand.setStatus(demandDto.getStatus());
		demand.setDepartment(demandDto.getDepartment());

		Demand updatedDemand = this.demandRepository.save(demand);
		DemandDto resultDemandDto = this.demandToDto(updatedDemand);
		return resultDemandDto;
	}

	/**
	 * Read Single Demand.
	 * 
	 * @param demandId
	 * @return DemandDto
	 * @exception DemandIdNotFoundException
	 */

	public DemandDto getDemandById(int demandId) {

		Demand demand = this.demandRepository.findById(demandId)
				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));

		return this.demandToDto(demand);
	}

	// Read All Demands.

	public List<DemandDto> getAllDemands(int page, int size) {

		Pageable paging = PageRequest.of(page, size, Sort.by("Id").descending());
		Page<Demand> demands = demandRepository.findAll(paging);
		List<DemandDto> demandDtos = demands.stream()

				.map(demand -> this.demandToDto(demand)).collect(Collectors.toList());
		return demandDtos;
	}

	// Delete Demand By Id.

	public void deleteDemand(Integer demandId) {
		Demand demand = this.demandRepository.findById(demandId)
				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));
		this.demandRepository.delete(demand);
	}

	private Demand dtoToDemand(DemandDto demandDto) {

		Demand demand = this.modelMapper.map(demandDto, Demand.class);

		return demand;

	}

	public DemandDto demandToDto(Demand demand) {

		DemandDto demandDto = this.modelMapper.map(demand, DemandDto.class);

		return demandDto;
	}

	@Async
	public void save(MultipartFile file) {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet datatypeSheet = workbook.getSheetAt(0);
			SheetReader<DemandsExcel> sr = new SheetReader<DemandsExcel>(workbook);
			sr.setHeaderRow(0);

			List<DemandsExcel> excels = sr.retrieveRows(datatypeSheet, DemandsExcel.class);

			List<Demand> excel = excels.stream().map(exc -> modelMapper.map(exc, Demand.class))
					.collect(Collectors.toList());
			demandRepository.saveAll(excel);
		} catch (Exception e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}

	}

	public List<ProfileDto> getProfilesFromDemand(int demandId) {
//		Demand demand = demandRepository.findById(demandId)
//	            .orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));
//
//	    List<ProfileDto> profiles = demand.getProfiles()
//	            .stream()
//	            .filter(profile -> profile.getAvailability().equalsIgnoreCase("Assigned"))
//	            .filter(profile -> !isProfileRejectedByDemandId(demandId, profile.getId()))
//	            .map(profile -> modelMapper.map(profile, ProfileDto.class))
//	            .collect(Collectors.toList());
//
//	    return profiles;
		
		List<ProfileDto> profiles = new ArrayList<>();

	    // Retrieve DemandProfileStatus instances with the given demandId and "Assigned" status
	    List<DemandProfileStatus> demandProfileStatusList = demandProfileStatusRepository.findByDemandidAndStatus(demandId, "Assigned");

	    for (DemandProfileStatus dps : demandProfileStatusList) {
	        // Retrieve the corresponding profile based on profileid
	        Profiles profile = profileRepository.findById(dps.getProfileid())
	                .orElseThrow(NoSuchElementException::new);

	        // Map the profile to ProfileDto and add it to the result list
	        ProfileDto profileDto = modelMapper.map(profile, ProfileDto.class);
	        profiles.add(profileDto);
	    }

	    return profiles;
	}

	private boolean isProfileRejectedByDemandId(int demandId, long profileId) {
	    DemandProfileStatus dps = demandProfileStatusRepository.findByDemandIdAndProfileId(demandId, profileId);
	    return dps != null && "rejected".equalsIgnoreCase(dps.getStatus());
	}

	public void addProfilesListStatus(ProfilesListDto profilesList, int demandId) {
		List<Profiles> profiles = profilesList.getProfilesList();

	    for (Profiles profile : profiles) {
	        long profileId = profile.getId();

	        DemandProfileStatus dps = demandProfileStatusRepository.findByDemandIdAndProfileId(demandId, profileId);
	        if (dps != null) {
	            String profileStatus = profile.getProfileStatus();
	            dps.setStatus(profileStatus);

	            demandProfileStatusRepository.save(dps);
	           
	            if ("rejected".equals(profileStatus)) {
	            	
	                profile.setAvailability("Available");
	                

	                // Save the updated profile
	                profileRepository.save(profile);
	            }
	           
	        }
	    }

	}
//	public void addProfilesListStatus(ProfilesListDto profilesList, int demandId) {
//		// TODO Auto-generated method stub
//
//		Demand demand = demandRepository.findById(demandId)
//				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));
//		List<Profiles> status = profilesList.getProfilesList();
//
//		List<Profiles> rejectedProfile = status.stream()
//				.filter(profile -> profile.getProfileStatus().equalsIgnoreCase("onhold")).map(profile -> {
////												List<Demand> profileDemandStatus = profile.getDemandRejectedStatus();
////												profileDemandStatus.add(demand);
////												profile.setDemandRejectedStatus(profileDemandStatus);
//					profile.setAvailability("Available");
//					return profile;
//				}).collect(Collectors.toList());
//		List<Profiles> acceptedProfile = status.stream()
//				.filter(profile -> profile.getProfileStatus().equalsIgnoreCase("accepted"))
//				.collect(Collectors.toList());
//		profileRepository.saveAll(rejectedProfile);
//		profileRepository.saveAll(acceptedProfile);
//
//	}

//	public void addProfilesToDemand(DemandDto profilesList, int demandId) {
//
//		Demand demand = demandRepository.findById(demandId)
//				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));
//
//		demand.setProfiles(profilesList.getProfilesList());
//
//		demandRepository.save(demand);
//
//	}
	

    public DemandService(DemandProfileStatusRepository demandProfileStatusRepository) {
        this.demandProfileStatusRepository = demandProfileStatusRepository;
    }
	public void addProfilesListToDemand(ProfilesListDto profilesList, int demandId) {

		  List<Long> profileIds = profilesList.getProfilesList()
		            .stream()
		            .map(Profiles::getId)
		            .collect(Collectors.toList());

        for (long profileId : profileIds) {
        	// Check if the record already exists
            DemandProfileStatus existingRecord = demandProfileStatusRepository.findByDemandIdAndProfileId(demandId, profileId);
            if (existingRecord == null) {
                DemandProfileStatus dps = new DemandProfileStatus();
                dps.setDemandid(demandId);
                dps.setProfileid(profileId);
                dps.setStatus("Assigned");
                demandProfileStatusRepository.save(dps);
            }        
        }
		
//		Demand demand = demandRepository.findById(demandId)
//				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));
//		List<Profiles> assignedProfile = demand.getProfiles();
//		List<Profiles> assign = profilesList.getProfilesList().stream().map(profile -> {
//			profile.setAvailability("Assigned");
//			return profile;
//		}
//		).collect(Collectors.toList());
//
//		List<Profiles> assignProfiles = new ArrayList<>();
//		assignProfiles.addAll(assignedProfile);
//		assignProfiles.addAll(assign);
//		profileRepository.saveAll(assignProfiles);
//		demand.setProfiles(assignProfiles);
//		demandRepository.save(demand);

	}

//	public void addProfilesListToDemand(ProfilesListDto profilesList, int demandId) {
//
//		Demand demand = demandRepository.findById(demandId)
//				.orElseThrow(() -> new DemandIdNotFoundException("Demand with id: " + demandId + " not found"));
//		
//		List<Profiles> assignedProfile = demand.getProfiles(); 
//		List<Profiles> assign= profilesList.getProfilesList().stream().map(profile -> {
//									profile.setAvailability("Assigned");
//									return profile;
//								})
//				
//				
//				.collect(Collectors.toList());
//		List<Profiles> assignProfiles= new ArrayList<>();
//		assignProfiles.addAll(assignedProfile);
//		assignProfiles.addAll(assign);
//		profileRepository.saveAll(assignProfiles);
//		demand.setProfiles(assignProfiles);
//		demandRepository.save(demand);
//
//	}

}