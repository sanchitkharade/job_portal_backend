package com.Jobportal.service;

import java.util.List;

import com.Jobportal.dto.ApplicantDTO;
import com.Jobportal.dto.Application;
import com.Jobportal.dto.JobDTO;
import com.Jobportal.exception.JobPortalException;

public interface JobService {

	public JobDTO postJob( JobDTO jobDTO) throws JobPortalException;

	public List<JobDTO> getAllJobs() throws JobPortalException;

	public JobDTO getJob(Long id) throws JobPortalException;

	public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException;

	public List<JobDTO> getJobsPostedby(Long id) throws JobPortalException ;

	public void changeAppStatus(Application application) throws JobPortalException;
	
}
