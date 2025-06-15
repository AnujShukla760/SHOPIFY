package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.eccomerce.entity.ecom.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
