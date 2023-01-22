package com.profiles.app.service;

import java.util.List;

import com.profiles.app.dto.ProfileDto;
import com.profiles.app.model.Profile;

public interface ProfileService {

	Profile save(ProfileDto profile);

	List<Profile> getAllprofiles();

	List<Profile> getAllprofilesByPrimarySkill(String skill);

	List<Profile> getAllprofilesByAvailability(String availability);

	List<Profile> getAllprofilesBylocation(String location);

	List<Profile> getAllProfilesByName(String search);

	List<Profile> getAllProfilesByProposedBy(String search);

	List<Profile> getAllProfilesBySource(String search);

}
