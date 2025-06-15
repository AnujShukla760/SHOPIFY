package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.ecom.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long>{

	@Query("SELECT sc FROM WishList sc "
			+ "LEFT JOIN FETCH sc.wishListItems items "
			+ "LEFT JOIN FETCH items.product "
			+ "WHERE sc.id= :wishListId")
	WishList findllWishListItems(Long wishListId);

}
