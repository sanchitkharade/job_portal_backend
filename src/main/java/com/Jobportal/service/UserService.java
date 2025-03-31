package com.Jobportal.service;

import com.Jobportal.dto.LoginDTO;
import com.Jobportal.dto.ResponseDTO;
import com.Jobportal.dto.UserDTO;
import com.Jobportal.exception.JobPortalException;


public interface UserService {
	public UserDTO registerUser(UserDTO userDTO) throws JobPortalException;
	
	public UserDTO getUserByEmail(String email)throws JobPortalException;

	public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException;

	public boolean sendOtp(String email) throws Exception;

	public boolean verifyOtp(String email, String otp) throws JobPortalException;

	public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException;
}
