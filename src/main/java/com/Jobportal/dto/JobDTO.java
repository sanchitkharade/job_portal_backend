package com.Jobportal.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.Jobportal.entity.Job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
	private String jobTitle;
	private String company;
	private List<ApplicantDTO> applicants;
	private String about;
	private String experience;
	private String jobType;
	private String location;
	private Long packageOffered;
	private LocalDateTime posttime;
	private String description;
	private List<String> skillsRequired;
	private jobStatus jobStatus;
	private Long postedBy;
	
	public Job toEntity() {
		return new Job(this.id,this.jobTitle,this.company,this.applicants!=null?this.applicants.stream().map((x)->x.toEntity()).toList():null,this.about,this.experience,this.jobType,this.location,this.packageOffered,this.posttime,this.description,this.skillsRequired,this.jobStatus,this.postedBy);
	}
}
