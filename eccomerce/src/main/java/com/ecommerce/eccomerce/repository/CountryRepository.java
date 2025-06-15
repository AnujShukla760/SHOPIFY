package com.ecommerce.eccomerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.eccomerce.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	@Query("from Country where countryName =:searchName")
	List<Country> findByName(String searchName);
	
	List<Country> findByCountryName(String searchName);

}
