package com.Jobportal.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Jobportal.dto.LoginDTO;
import com.Jobportal.dto.NotificationDTO;
import com.Jobportal.dto.ResponseDTO;
import com.Jobportal.dto.UserDTO;
import com.Jobportal.entity.OTP;
import com.Jobportal.entity.User;
import com.Jobportal.exception.JobPortalException;
import com.Jobportal.repository.OTPRepository;
import com.Jobportal.repository.UserRepository;
import com.Jobportal.utility.Data;
import com.Jobportal.utility.Utilities;
import jakarta.mail.internet.MimeMessage;

@Service(value = "userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private OTPRepository otpRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
		Optional<User> optional= userRepository.findByEmail(userDTO.getEmail());
		if (optional.isPresent())throw new JobPortalException("USER_FOUND");
		userDTO.setProfileId(profileService.createProfile(userDTO.getEmail(),userDTO.getName()));
		userDTO.setId(Utilities.getNextSeq("users"));
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user=userDTO.toEntity();
		user= userRepository.save(user);
		return user.toDTO();
	}

	@Override
	public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
		if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) throw new JobPortalException("INVALID_CREDENTIALS");
		return user.toDTO();
	}

	@Override
	public boolean sendOtp(String email) throws Exception {
		User user=userRepository.findByEmail(email).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
		MimeMessage mm= mailSender.createMimeMessage();
		MimeMessageHelper message= new MimeMessageHelper(mm,true);
		message.setTo(email);
		message.setSubject("Your OTP Code");
		String genOtp=Utilities.generateOTP();
		OTP otp=new OTP(email,genOtp,LocalDateTime.now());
		otpRepository.save(otp);
		message.setText(Data.getMessageBody(genOtp,user.getName()),true);
		mailSender.send(mm);
		return true;
	}

	@Override
	public boolean verifyOtp(String email,String otp) throws JobPortalException {
		OTP otpEntity=otpRepository.findById(email).orElseThrow(()->new JobPortalException("OTP_NOT_FOUND"));
		if (!otpEntity.getOtpCode().equals(otp))throw new JobPortalException("OTP_INCORRECT"); 
		return true;
	}

	@Override
	public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException {
		User user=userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
		user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
		userRepository.save(user);
		NotificationDTO noti=new NotificationDTO();
		noti.setUserId(user.getId());
		noti.setMessage("Password Reset Succeessfully");
		noti.setAction("Password Resest");
		notificationService.sendNotification(noti);
		return new ResponseDTO("Password Changed Successfully");
	}
	
	@Scheduled(fixedRate = 60000)
	public void removeExpiredOTPs() {
		LocalDateTime expiry=LocalDateTime.now().minusMinutes(5);
		List<OTP>expiredOtps=otpRepository.findByCreationTimeBefore(expiry);
		if(!expiredOtps.isEmpty()) {
			otpRepository.deleteAll(expiredOtps);
			System.out.println("Removed "+expiredOtps.size()+" Expired OTPs");
		}
	}

	@Override
	public UserDTO getUserByEmail(String email) throws JobPortalException {
		User user=userRepository.findByEmail(email).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
		return user.toDTO();
	}

}
