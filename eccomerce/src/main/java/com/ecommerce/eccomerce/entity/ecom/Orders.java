package com.ecommerce.eccomerce.entity.ecom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ecommerce.eccomerce.entity.AddressEmbeddable;
import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.enums.OrderStatus;
import com.ecommerce.eccomerce.enums.PaymentMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private AddressEmbeddable addressEmbeddable;

	@DateTimeFormat
	private LocalDateTime orderDateTime;

	@ManyToOne
	private Users users;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	private Double totalPrice;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Lob
	private String orderCancelReason;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
	List<OrderItemList> orderItemLists = new ArrayList<OrderItemList>();

}
