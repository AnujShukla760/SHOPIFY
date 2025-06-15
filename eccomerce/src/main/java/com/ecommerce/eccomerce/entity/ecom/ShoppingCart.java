package com.ecommerce.eccomerce.entity.ecom;

import java.util.List;

import com.ecommerce.eccomerce.entity.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ShoppingCart {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Users users;
	
	@OneToMany(mappedBy = "shoppingCart")
	List<ShoppingCartList> shoppingCartLists;
	
}
