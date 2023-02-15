package com.application.profile.logic;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.profile.domain.model.Profiles;
import com.application.profile.domain.repository.ProfileRepository;
import com.application.profile.service.rest.v1.model.ProfileDto;
import com.xlm.reader.SheetReader;



@Service
public class ProfileService{
	
	
	@Autowired
	private ProfileRepository profileRepository;

	public Profiles save(ProfileDto profile) {
		
		Profiles addedProfile = new Profiles();
		
		addedProfile.setName(profile.getName());
		addedProfile.setPrimarySkill(profile.getPrimarySkill());
		addedProfile.setLocation(profile.getLocation());
		addedProfile.setAvailability(profile.getAvailability());
		addedProfile.setProposedBy(profile.getProposedBy());
		addedProfile.setSource(profile.getSource());
		
		return profileRepository.save(addedProfile);

	}

	public List<Profiles> getAllprofiles() {
		
		return profileRepository.findAll();
	}

	
	public List<Profiles> getAllprofilesByPrimarySkill(String skill) {
		
		return profileRepository.findAllByPrimarySkill(skill);
	}

	
	public List<Profiles> getAllprofilesByAvailability(String availability) {
		
		return profileRepository.findAllByAvailability(availability);
	}

	
	public List<Profiles> getAllprofilesBylocation(String location) {

		return profileRepository.findAllByLocation(location);
	}


	public List<Profiles> getAllProfilesByName(String search) {

		return profileRepository.findAllByName(search);
		
	}


	public List<Profiles> getAllProfilesByProposedBy(String search) {
		return profileRepository.findAllByProposedBy(search);
	}

	public List<Profiles> getAllProfilesBySource(String search) {
		
		return profileRepository.findAllBySource(search);
		
	}

	@Async
	public void save(MultipartFile file) {
	    try {
	    	
	      
	    	XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet datatypeSheet = workbook.getSheetAt(0);
	        SheetReader<Profiles> sr=new SheetReader<Profiles>(workbook);
	        sr.setHeaderRow(0);
	       
	        List<Profiles> excels = sr.retrieveRows(datatypeSheet,Profiles.class);
	        
			profileRepository.saveAll(excels);
	    } catch (Exception e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	  }

}