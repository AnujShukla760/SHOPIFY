package com.ecommerce.eccomerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.State;
import com.ecommerce.eccomerce.repository.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;

	public void saveState(State state) {
		stateRepository.save(state);
	}

	public State findStateById(Long stateId) {
		return stateRepository.findById(stateId).orElseThrow();
	}

	public void deleteStateById(Long id) {
		stateRepository.delete(findStateById(id));
	}

	public List<State> findAllState() {
		return stateRepository.findAll();
	}

	public List<State> findStatesByCountryId(Long countryId) {
		return stateRepository.findStatesByCountryId(countryId);
	}
	

}
