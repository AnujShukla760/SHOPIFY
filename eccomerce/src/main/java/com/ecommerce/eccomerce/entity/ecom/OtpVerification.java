package com.ecommerce.eccomerce.entity.ecom;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.ecommerce.eccomerce.enums.OtpStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor  // <--- Add this
@AllArgsConstructor // <--- Add this for builder and convenience
public class OtpVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String otp;

	@CreationTimestamp
	private LocalDateTime localDateTime;

	@Enumerated(EnumType.STRING)
	private OtpStatus otpStatus;

	private String email;

}
