package com.ecommerce.eccomerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.ecom.Tax;
import com.ecommerce.eccomerce.entity.ecom.TaxPercentage;
import com.ecommerce.eccomerce.repository.TaxPercentageRepository;
import com.ecommerce.eccomerce.repository.TaxRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxAndTaxPercentageService {

	private final TaxRepository taxRepository;
	private final TaxPercentageRepository taxPercentageRepository;

	// -------------------------------- TAX SECTION ----------------------------------

	public List<Tax> findAllTaxes() {
		return taxRepository.findAll();
	}

	public void saveTax(Tax tax) {
		taxRepository.save(tax);
	}

	public Tax findTaxById(Long id) {
		return taxRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tax not found"));
	}

	public void deleteTaxById(Long id) {
		taxRepository.deleteById(id);
	}

	// -------------------------- TAX PERCENTAGE SECTION ----------------------------

	public List<TaxPercentage> findAllTaxPercentages() {
		return taxPercentageRepository.findAll();
	}

	public void saveTaxPercentage(TaxPercentage taxPercentage) {
		taxPercentageRepository.save(taxPercentage);
	}

	public TaxPercentage findTaxPercentageById(Long id) {
		return taxPercentageRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tax Percentage not found"));
	}

	public void deleteTaxPercentageById(Long id) {
		taxPercentageRepository.deleteById(id);
	}
}
