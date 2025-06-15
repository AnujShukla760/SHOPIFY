package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.eccomerce.entity.ecom.TaxPercentage;

public interface TaxPercentageRepository extends JpaRepository<TaxPercentage, Long> {

}
