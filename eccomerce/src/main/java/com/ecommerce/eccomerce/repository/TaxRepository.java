package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.eccomerce.entity.ecom.Tax;

public interface TaxRepository extends JpaRepository<Tax, Long> {

}
