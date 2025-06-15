package com.ecommerce.eccomerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.District;
import com.ecommerce.eccomerce.repository.DistrictRepository;

@Service
public class DistrictService {
	
	@Autowired 
	private DistrictRepository districtRepository;
	
	public void saveDistrict (District district) {
		districtRepository.save(district);
	}
	
	public District findDistrictById(Long districtId) {
		return districtRepository.findById(districtId).orElseThrow();
	}
	public void deleteDistrictById(Long id) {
		districtRepository.deleteById(id);
	}

	public List<District> findAllDistrict() {
		return districtRepository.findAll();
	}

	public List<District> findAllDistrictsByStateId(Long stateId) {

		return districtRepository.findByStateId(stateId);
	}

}
