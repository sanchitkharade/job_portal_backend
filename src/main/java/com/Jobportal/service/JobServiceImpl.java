package com.Jobportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Jobportal.dto.ApplicantDTO;
import com.Jobportal.dto.Application;
import com.Jobportal.dto.ApplicationStatus;
import com.Jobportal.dto.JobDTO;
import com.Jobportal.dto.NotificationDTO;
import com.Jobportal.dto.jobStatus;
import com.Jobportal.entity.Applicant;
import com.Jobportal.entity.Job;
import com.Jobportal.exception.JobPortalException;
import com.Jobportal.repository.JobRepository;
import com.Jobportal.utility.Utilities;

@Service("jobService")
public class JobServiceImpl implements JobService {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private NotificationService notificationService;

	@Override
	public JobDTO postJob(JobDTO jobDTO) throws JobPortalException {
		if(jobDTO.getId()==0) {
			jobDTO.setId(Utilities.getNextSeq("jobs"));
			jobDTO.setPosttime(LocalDateTime.now());
			NotificationDTO notiDTO= new NotificationDTO();
			notiDTO.setAction("Job Posted");
			notiDTO.setMessage("Job Posted Successfully for "+jobDTO.getJobTitle()+" at "+jobDTO.getCompany());
			notiDTO.setUserId(jobDTO.getPostedBy());
			notiDTO.setRoute("/posted-jobs/"+jobDTO.getId());
			notificationService.sendNotification(notiDTO);
		}
		else {
			Job job= jobRepository.findById(jobDTO.getId()).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
			if(job.getJobStatus().equals(jobStatus.DRAFT)||jobDTO.getJobStatus().equals(jobStatus.CLOSED))jobDTO.setPosttime(LocalDateTime.now());
		}
		return jobRepository.save(jobDTO.toEntity()).toDTO();
	}

	@Override
	public List<JobDTO> getAllJobs() throws JobPortalException {
		return jobRepository.findAll().stream().map((x)->x.toDTO()).toList();
	}

	@Override
	public JobDTO getJob(Long id) throws JobPortalException {
		return jobRepository.findById(id).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND")).toDTO();
	}

	@Override
	public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException {
		Job job= jobRepository.findById(id).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
		List<Applicant>applicants=job.getApplicants();
		if (applicants==null) applicants=new ArrayList<>();
		if(applicants.stream().filter((x)->x.getApplicantId()==applicantDTO.getApplicantId()).toList().size()>0) throw new JobPortalException("JOB_APPLIED_ALREADY");
		applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
		applicants.add(applicantDTO.toEntity());
		job.setApplicants(applicants);
		jobRepository.save(job);
	}

	@Override
	public List<JobDTO> getJobsPostedby(Long id) throws JobPortalException {
		return jobRepository.findByPostedBy(id).stream().map((x)->x.toDTO()).toList();
	}

	@Override
	public void changeAppStatus(Application application) throws JobPortalException {
		Job job= jobRepository.findById(application.getId()).orElseThrow(()->new JobPortalException("JOB_NOT_FOUND"));
		List<Applicant>applicants=job.getApplicants().stream().map((x)->{
			if (application.getApplicantId()==x.getApplicantId()) {
			x.setApplicationStatus(application.getApplicationStatus());
			if (application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)) {
				x.setInterviewTime(application.getInterviewTime());
				NotificationDTO notiDTO= new NotificationDTO();
				notiDTO.setAction("Interview scheduled");
				notiDTO.setMessage("Interview scheduled for job id: "+application.getApplicantId());
				notiDTO.setUserId(application.getApplicantId());
				notiDTO.setRoute("job-history");
				try {
					notificationService.sendNotification(notiDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
			return x;
			}).toList();
		job.setApplicants(applicants);
		jobRepository.save(job);
		
	}
}
