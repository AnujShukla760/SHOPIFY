package com.ecommerce.eccomerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.eccomerce.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
	
	public List<District> findByStateId(Long stateId);

}
