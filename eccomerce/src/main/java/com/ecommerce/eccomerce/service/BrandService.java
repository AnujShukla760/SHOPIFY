package com.ecommerce.eccomerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.ecom.Brand;
import com.ecommerce.eccomerce.repository.BrandRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {

	private final BrandRepository brandRepository;

	public void saveBrand(Brand brand) {
		brandRepository.save(brand);
	}

	public Brand findBrandById(Long id) {
		return brandRepository.findById(id).orElseThrow();
	}

	public void deleteBrandById(Long id) {
		brandRepository.deleteById(id);
	}

	public List<Brand> findAllBrand() {
		return brandRepository.findAll();
	}
}
