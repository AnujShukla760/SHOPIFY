package com.ecommerce.eccomerce.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.eccomerce.entity.ecom.OtpVerification;
import com.ecommerce.eccomerce.enums.OtpStatus;
import com.ecommerce.eccomerce.repository.OtpVerificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpVerificationService {

	private final OtpVerificationRepository otpVerificationRepository;

	@Transactional
	public void saveVerification(String regsiteredEmail, String sendOtp) {
		
		otpVerificationRepository.updateOtpStatusExpiry(regsiteredEmail , OtpStatus.EXPIRED);
		
		OtpVerification otpVerification = OtpVerification.builder()
		
				.email(regsiteredEmail)
				.otp(sendOtp)
				.localDateTime(LocalDateTime.now())
				.otpStatus(OtpStatus.ACTIVE)
				.build();
		otpVerificationRepository.save(otpVerification);		
	}

	public boolean validateOtp(String email, String insertOtp) {
		return otpVerificationRepository.findByEmailAndOtpAndOtpStatus(email , insertOtp , OtpStatus.ACTIVE).isPresent();
		
	}
}
