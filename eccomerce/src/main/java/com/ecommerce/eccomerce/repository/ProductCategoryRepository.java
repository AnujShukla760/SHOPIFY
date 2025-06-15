package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.eccomerce.entity.ecom.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
