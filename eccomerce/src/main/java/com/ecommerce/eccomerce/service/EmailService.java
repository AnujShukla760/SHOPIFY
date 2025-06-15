package com.ecommerce.eccomerce.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String mainEmail;
	
	@Async
	public void sendEmailToCustomer(String email) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(mainEmail);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("Shopify Orders");
		simpleMailMessage.setText("Your Order Successfully Placed");
		javaMailSender.send(simpleMailMessage);
	}

	@Async
	public void sendOtp(String regsiteredEmail, String sendOtp) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(mainEmail);
		simpleMailMessage.setTo(regsiteredEmail);
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("OTP Verification (Shopify...)");
		simpleMailMessage.setText("We received a request to reset your password Please use the following One-Time Password (OTP) to continue:"+sendOtp);
		javaMailSender.send(simpleMailMessage);

	}

}
