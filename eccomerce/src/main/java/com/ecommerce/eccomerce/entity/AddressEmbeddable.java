package com.ecommerce.eccomerce.entity;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AddressEmbeddable {

	private String email;
	private String mobileNo;
	private String city;
	private String landMark;
	private String pincode;
	
	@ManyToOne
	private District district;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	public String getFullAddress() {
		StringBuilder sb = new StringBuilder();

		if (landMark != null && !landMark.isEmpty()) sb.append(landMark).append(", ");
		if (city != null && !city.isEmpty()) sb.append(city).append(", ");
		if (district != null && district.getDistrictName() != null) sb.append(district.getDistrictName()).append(", ");
		if (pincode != null && !pincode.isEmpty()) sb.append("PIN: ").append(pincode);
		
		return sb.toString();
	}
	
	
}
