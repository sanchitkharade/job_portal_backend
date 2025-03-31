package com.Jobportal.service;

import java.util.List;

import com.Jobportal.dto.NotificationDTO;
import com.Jobportal.entity.Notification;
import com.Jobportal.exception.JobPortalException;

public interface NotificationService {
	
	public void sendNotification(NotificationDTO notificationDTO) throws JobPortalException;
	public List<Notification>getunreadNotifications(Long userId);
	public void readNotification(Long id) throws JobPortalException;
}
