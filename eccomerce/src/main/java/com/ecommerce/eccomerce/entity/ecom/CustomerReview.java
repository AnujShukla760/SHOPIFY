package com.ecommerce.eccomerce.entity.ecom;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.eccomerce.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CustomerReview {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int rating;

	@Lob
	private String comment;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;

	@Transient
	private MultipartFile file;

	@ManyToOne
	private Users users;

	@ManyToOne
	private Product product;
	
	@CreationTimestamp
	private LocalDateTime localDateTime;

}
