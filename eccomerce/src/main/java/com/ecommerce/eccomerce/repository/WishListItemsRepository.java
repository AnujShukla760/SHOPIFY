package com.ecommerce.eccomerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.WishList;
import com.ecommerce.eccomerce.entity.ecom.WishListItems;

public interface WishListItemsRepository extends JpaRepository<WishListItems, Long> {

	Optional<WishListItems> findByWishListAndProduct(WishList wishList, Product product);

	@Query("SELECT SUM(s.quantity) FROM WishListItems s where s.wishList.id= :wishListId")
	Integer getAllWishListCount(Long wishListId);

}
