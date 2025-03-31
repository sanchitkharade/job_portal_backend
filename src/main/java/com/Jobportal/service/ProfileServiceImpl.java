package com.Jobportal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Jobportal.dto.ProfileDTO;
import com.Jobportal.entity.Profile;
import com.Jobportal.exception.JobPortalException;
import com.Jobportal.repository.ProfileRepository;
import com.Jobportal.utility.Utilities;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Long createProfile(String email,String name) throws JobPortalException {
		Profile profile= new Profile();
		profile.setId(Utilities.getNextSeq("profiles"));
		profile.setEmail(email);
		profile.setName(name);
		profile.setSkills(new ArrayList<>());
		profile.setExperiences(new ArrayList<>());
		profile.setCertifications(new ArrayList<>());
		profileRepository.save(profile);
		return profile.getId();
		}

	@Override
	public ProfileDTO getProfile(Long id) throws JobPortalException {
		return profileRepository.findById(id).orElseThrow(()->new JobPortalException("PROFILE_NOT_FOUND")).toDto();
	}

	@Override
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
		profileRepository.findById(profileDTO.getId()).orElseThrow(()->new JobPortalException("PROFILE_NOT_FOUND"));
		profileRepository.save(profileDTO.toEntity());
		return profileDTO;
	}

	@Override
	public List<ProfileDTO> getAllProfile() throws JobPortalException {
		return profileRepository.findAll().stream().map((x)->x.toDto()).toList();
	}

}
