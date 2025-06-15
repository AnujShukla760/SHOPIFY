package com.ecommerce.eccomerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.eccomerce.entity.ecom.CustomerReview;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long>{

	List<CustomerReview> findByProductId(Long id);

}
