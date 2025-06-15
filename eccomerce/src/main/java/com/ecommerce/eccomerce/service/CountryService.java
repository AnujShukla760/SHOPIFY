package com.ecommerce.eccomerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.Country;
import com.ecommerce.eccomerce.repository.CountryRepository;

@Service
public class CountryService {
	
	@Autowired
	private CountryRepository countryRepository;
	

	public void saveCountry(Country country) {
		countryRepository.save(country);
	}
	
	public Country findCountryById(Long id) {
		return countryRepository.findById(id).orElseThrow();
	}
	public void deleteCountryById(Long id) {
		countryRepository.deleteById(id);
	}

	public List<Country> findAllCountry() {
		return countryRepository.findAll();
	}

	public List<Country> findByCountryName(String searchName) {
		return countryRepository.findByCountryName(searchName);
	}
	
	
	
	
	

}
