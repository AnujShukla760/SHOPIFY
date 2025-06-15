package com.ecommerce.eccomerce.entity.ecom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.eccomerce.entity.AddressEmbeddable;
import com.ecommerce.eccomerce.enums.PaymentMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String billNo;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] billImage;

	@Transient
	private MultipartFile billFile;

	@DateTimeFormat
	private LocalDateTime purchaseDateAndTime;

	private Double totalPrice;

	private String vendorName;

	@Embedded
	private AddressEmbeddable addressEmbeddable;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	List<PurchaseItems> purchaseItems = new ArrayList<PurchaseItems>();

}
