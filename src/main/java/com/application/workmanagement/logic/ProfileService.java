package com.application.workmanagement.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.common.exception.ResourceNotFoundException;
import com.application.workmanagement.domain.model.Profiles;
import com.application.workmanagement.domain.model.ProfilesExcel;
import com.application.workmanagement.domain.repository.ProfileRepository;
import com.application.workmanagement.service.rest.v1.model.ProfileDto;
import com.xlm.reader.SheetReader;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ProfileDto save(ProfileDto profile) {

		Profiles profiles = modelMapper.map(profile, Profiles.class);

		profileRepository.save(profiles);
		return profile;

	}

	public List<ProfileDto> getAllprofiles() {

		return profileRepository.findAll().stream().map(profile -> modelMapper.map(profile, ProfileDto.class))
				.collect(Collectors.toList());
	}

	public List<ProfileDto> getAllprofilesByPrimarySkill(String skill) {

		return profileRepository.findAllByPrimarySkill(skill).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllprofilesByAvailability(String availability) {

		return profileRepository.findAllByAvailability(availability).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllprofilesBylocation(String location) {

		return profileRepository.findAllByLocation(location).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllProfilesByName(String search) {

		return profileRepository.findAllByName(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());

	}

	public List<ProfileDto> getAllProfilesByProposedBy(String search) {
		return profileRepository.findAllByProposedBy(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllProfilesBySource(String search) {

		return profileRepository.findAllBySource(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());

	}

	@Async
	public void save(MultipartFile file) {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet datatypeSheet = workbook.getSheetAt(0);
			SheetReader<ProfilesExcel> sr = new SheetReader<ProfilesExcel>(workbook);
			sr.setHeaderRow(0);

			List<ProfilesExcel> excels = sr.retrieveRows(datatypeSheet, ProfilesExcel.class);
			List<Profiles> excel = excels.stream().map(profile -> modelMapper.map(profile, Profiles.class)).collect(Collectors.toList());
			profileRepository.saveAll(excel);
		} catch (Exception e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public List<ProfileDto> getAllprofilesByPrimarySkillIgnoreCase(String search) {
		return profileRepository.findByPrimarySkillIgnoreCase(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllprofilesByAvailabilityIgnoreCase(String search) {
		return profileRepository.findByAvailabilityIgnoreCase(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllprofilesBylocationIgnoreCase(String search) {
		return profileRepository.findByLocationIgnoreCase(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllProfilesByNameIgnoreCase(String search) {
		return profileRepository.findByNameIgnoreCase(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllProfilesByProposedByIgnoreCase(String search) {
		return profileRepository.findByProposedByIgnoreCase(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getAllProfilesBySourceIgnoreCase(String search) {
		return profileRepository.findBySourceIgnoreCase(search).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());
	}

	public List<ProfileDto> getProfilesBasedOnSkillAndAvailability(String skill, String available) {
		List<ProfileDto> profiles = profileRepository.findAllByPrimarySkill(skill).stream()
				.map(profile -> modelMapper.map(profile, ProfileDto.class)).collect(Collectors.toList());

		return profiles.stream().filter(profile -> profile.getAvailability().equalsIgnoreCase(available))
				.collect(Collectors.toList());

	}

	public ProfileDto getProfileById(long id) {
		Profiles profile = profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile", "id", id));

		return modelMapper.map(profile, ProfileDto.class);

	}

	public void deleteProfilesByid(long id) {
		Profiles profile = profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile", "id", id));

		profileRepository.delete(profile);

	}

	public void updateProfilesById(long id, ProfileDto profile) {
		Profiles updatedProfile = profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile", "id", id));
		updatedProfile.setLocation(profile.getLocation());
		updatedProfile.setAvailability(profile.getAvailability());
		updatedProfile.setName(profile.getName());
		updatedProfile.setPrimarySkill(profile.getPrimarySkill());
		updatedProfile.setProposedBy(profile.getProposedBy());
		updatedProfile.setSource(profile.getSource());
		profileRepository.save(updatedProfile);

	}

}