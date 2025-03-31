package com.Jobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Jobportal.dto.NotificationDTO;
import com.Jobportal.dto.NotificationStatus;
import com.Jobportal.entity.Notification;
import com.Jobportal.exception.JobPortalException;
import com.Jobportal.repository.NotificationRepository;
import com.Jobportal.utility.Utilities;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public void sendNotification(NotificationDTO notificationDTO)throws JobPortalException {
		notificationDTO.setId(Utilities.getNextSeq("notification"));
		notificationDTO.setStatus(NotificationStatus.UNREAD);
		notificationDTO.setTimestamp(LocalDateTime.now());
		notificationRepository.save(notificationDTO.toEntity());
		
	}

	@Override
	public List<Notification> getunreadNotifications(Long userId) {
		return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
	}

	@Override
	public void readNotification(Long id) throws JobPortalException {
		Notification noti=notificationRepository.findById(id).orElseThrow(()->new JobPortalException("No Notification Found"));
		noti.setStatus(NotificationStatus.READ);
		notificationRepository.save(noti);
	}
}
