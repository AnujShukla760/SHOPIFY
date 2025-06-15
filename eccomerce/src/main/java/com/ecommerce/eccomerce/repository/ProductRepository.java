package com.ecommerce.eccomerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.ecom.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByproductSubCategoryId(Long id);

	@Modifying
	@Query("UPDATE Product set quantity = :quantity where id = :productId")
	void reduceStock(int quantity, Long productId);

	@Query("SELECT SUM(s.quantity) FROM Product s")
	Integer remainingStockCount();

}
