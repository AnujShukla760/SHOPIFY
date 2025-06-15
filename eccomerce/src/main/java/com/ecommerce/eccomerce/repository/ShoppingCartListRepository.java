package com.ecommerce.eccomerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCartList;

public interface ShoppingCartListRepository extends JpaRepository<ShoppingCartList, Long> {

	Optional<ShoppingCartList> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);

	@Query("SELECT SUM(s.quantity) from ShoppingCartList s where s.shoppingCart.id=:cartId")
	public Integer getAllCartCount(Long cartId);
	
	public List<ShoppingCartList> findByShoppingCart(ShoppingCart shoppingCart);

	@Query("SELECT SUM(s.quantity) from ShoppingCartList s where s.shoppingCart.id=:cartId and s.product.id=:productId")
	public int getAllProductCartCount(Long cartId , Long productId);
	
	
}
