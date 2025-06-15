package com.ecommerce.eccomerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.eccomerce.entity.ecom.ProductCategory;
import com.ecommerce.eccomerce.entity.ecom.ProductSubCategory;

public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Long> {

	List<ProductSubCategory> findByProductCategory(ProductCategory category);

	@Query("SELECT s FROM ProductSubCategory s WHERE LOWER(s.subCategoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<ProductSubCategory> searchBySubCategoryName(@Param("keyword") String keyword);
	
	@Query("SELECT s FROM ProductSubCategory s WHERE s.productCategory = :category AND LOWER(s.subCategoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<ProductSubCategory> searchByCategoryAndName(@Param("category") ProductCategory category, @Param("keyword") String keyword);

	List<ProductSubCategory> findBySubCategoryNameStartingWithIgnoreCase(String term);


}
