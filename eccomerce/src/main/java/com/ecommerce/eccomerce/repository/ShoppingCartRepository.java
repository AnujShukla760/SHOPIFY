package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	@Query("SELECT sc FROM ShoppingCart sc "
			+ "LEFT JOIN FETCH sc.shoppingCartLists items "
			+ "LEFT JOIN FETCH items.product "
			+ "WHERE sc.id= :cartId")
	ShoppingCart findllShoppingCartItems(Long cartId);

}
