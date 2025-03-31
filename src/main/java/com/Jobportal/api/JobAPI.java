package com.Jobportal.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Jobportal.dto.ApplicantDTO;
import com.Jobportal.dto.Application;
import com.Jobportal.dto.JobDTO;
import com.Jobportal.dto.ResponseDTO;
import com.Jobportal.exception.JobPortalException;
import com.Jobportal.service.JobService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobAPI {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/post")
	public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDTO) throws JobPortalException {
		return new ResponseEntity<>(jobService.postJob(jobDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<JobDTO>> getAllJobs() throws JobPortalException {
		return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<JobDTO> getJob(@PathVariable Long id) throws JobPortalException {
		return new ResponseEntity<>(jobService.getJob(id), HttpStatus.OK);
	}
	
	@PostMapping("/apply/{id}")
	public ResponseEntity<ResponseDTO> applyJob(@PathVariable Long id, @RequestBody  ApplicantDTO applicantDTO) throws JobPortalException {
		jobService.applyJob(id,applicantDTO);
		return new ResponseEntity<>( new ResponseDTO("Applied Successfully") , HttpStatus.CREATED);
	}
	
	@GetMapping("/postedBy/{id}")
	public ResponseEntity<List<JobDTO>> getJobsPostedBy(@PathVariable Long id) throws JobPortalException {
		return new ResponseEntity<>(jobService.getJobsPostedby(id), HttpStatus.OK);
	}

	@PostMapping("/changeAppStatus")
	public ResponseEntity<ResponseDTO> changeAppStatus( @RequestBody  Application application) throws JobPortalException { 
		jobService.changeAppStatus(application);
		return new ResponseEntity<>( new ResponseDTO("Application Status changed Successfully") , HttpStatus.CREATED);
	}
}
