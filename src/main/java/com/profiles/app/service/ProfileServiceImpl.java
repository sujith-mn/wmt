package com.profiles.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profiles.app.dto.ProfileDto;
import com.profiles.app.model.Profile;
import com.profiles.app.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;

	@Override
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

	@Override
	public List<Profile> getAllprofiles() {
		
		return profileRepository.findAll();
	}

	@Override
	public List<Profile> getAllprofilesByPrimarySkill(String skill) {
		
		return profileRepository.findAllByPrimarySkill(skill);
	}

	@Override
	public List<Profile> getAllprofilesByAvailability(String availability) {
		
		return profileRepository.findAllByAvailability(availability);
	}

	@Override
	public List<Profile> getAllprofilesBylocation(String location) {

		return profileRepository.findAllByLocation(location);
	}

	@Override
	public List<Profile> getAllProfilesByName(String search) {

		return profileRepository.findAllByName(search);
		
	}

	@Override
	public List<Profile> getAllProfilesByProposedBy(String search) {
		return profileRepository.findAllByProposedBy(search);
	}

	@Override
	public List<Profile> getAllProfilesBySource(String search) {
		
		return profileRepository.findAllBySource(search);
		
	}

}
