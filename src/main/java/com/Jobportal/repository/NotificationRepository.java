package com.Jobportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Jobportal.entity.Notification;
import com.Jobportal.dto.NotificationStatus;



public interface NotificationRepository extends MongoRepository<Notification,Long>{
	public List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
}
