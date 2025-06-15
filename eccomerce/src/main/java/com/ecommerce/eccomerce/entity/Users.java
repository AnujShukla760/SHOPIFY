package com.ecommerce.eccomerce.entity;

import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;
import com.ecommerce.eccomerce.entity.ecom.WishList;
import com.ecommerce.eccomerce.enums.UsersCategory;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;
	private String password;

	@Enumerated(EnumType.STRING)
	private UsersCategory usersCategory;

	@Embedded
	private AddressEmbeddable addressEmbeddable;
	
	@OneToOne(mappedBy = "users")
	private WishList wishList;
	
	
	@OneToOne(mappedBy = "users")
	private ShoppingCart shoppingCart;
	
	

}
