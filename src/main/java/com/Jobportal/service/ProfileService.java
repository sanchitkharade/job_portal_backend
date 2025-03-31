package com.Jobportal.service;

import java.util.List;

import com.Jobportal.dto.ProfileDTO;
import com.Jobportal.exception.JobPortalException;

public interface ProfileService {
	public Long createProfile(String email,String name) throws JobPortalException;
	public ProfileDTO getProfile(Long id) throws JobPortalException;
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;
	public List<ProfileDTO> getAllProfile() throws JobPortalException;

}
