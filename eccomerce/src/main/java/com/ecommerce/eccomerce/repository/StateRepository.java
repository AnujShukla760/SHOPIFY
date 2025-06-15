package com.ecommerce.eccomerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.eccomerce.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

	@Query("from State where country.id = :countryId")
	public List<State> findStatesByCountryId(Long countryId);

	public Optional<State> findById(Long stateId);

}
