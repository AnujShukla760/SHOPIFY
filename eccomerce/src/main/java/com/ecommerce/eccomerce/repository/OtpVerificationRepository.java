package com.ecommerce.eccomerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.ecom.OtpVerification;
import com.ecommerce.eccomerce.enums.OtpStatus;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long>{

	@Modifying
	@Query("UPDATE OtpVerification o set o.otpStatus = :expired where o.email = :regsiteredEmail")
	void updateOtpStatusExpiry(String regsiteredEmail, OtpStatus expired);

	Optional<OtpVerification> findByEmailAndOtpAndOtpStatus(String email, String insertOtp, OtpStatus active);

}
