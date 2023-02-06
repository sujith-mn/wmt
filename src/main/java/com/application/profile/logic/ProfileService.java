package com.application.profile.logic;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.profile.domain.model.Profile;
import com.application.profile.domain.repository.ProfileRepository;
import com.application.profile.service.rest.v1.ExcelHelper;
import com.application.profile.service.rest.v1.model.ProfileDto;

@Service
public class ProfileService{
	
	@Autowired
	private ProfileRepository profileRepository;

	public Profile save(ProfileDto profile) {
		
		Profile addedProfile = new Profile();
		
		addedProfile.setName(profile.getName());
		addedProfile.setPrimarySkill(profile.getPrimarySkill());
		addedProfile.setLocation(profile.getLocation());
		addedProfile.setAvailability(profile.getAvailability());
		addedProfile.setProposedBy(profile.getProposedBy());
		addedProfile.setSource(profile.getSource());
		
		return profileRepository.save(addedProfile);

	}

	public List<Profile> getAllprofiles() {
		
		return profileRepository.findAll();
	}

	
	public List<Profile> getAllprofilesByPrimarySkill(String skill) {
		
		return profileRepository.findAllByPrimarySkill(skill);
	}

	
	public List<Profile> getAllprofilesByAvailability(String availability) {
		
		return profileRepository.findAllByAvailability(availability);
	}

	
	public List<Profile> getAllprofilesBylocation(String location) {

		return profileRepository.findAllByLocation(location);
	}


	public List<Profile> getAllProfilesByName(String search) {

		return profileRepository.findAllByName(search);
		
	}


	public List<Profile> getAllProfilesByProposedBy(String search) {
		return profileRepository.findAllByProposedBy(search);
	}

	public List<Profile> getAllProfilesBySource(String search) {
		
		return profileRepository.findAllBySource(search);
		
	}
	
	public void save(MultipartFile file) {
	    try {
	      List<Profile> excels = ExcelHelper.excelToData(file.getInputStream());
	      profileRepository.saveAll(excels);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	  }

}
