package com.Jobportal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Jobportal.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Long>{

}
