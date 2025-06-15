package com.ecommerce.eccomerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.eccomerce.entity.Organization;
import com.ecommerce.eccomerce.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {

	private final OrganizationRepository organizationRepository;

	public void save(Organization organization) throws IOException {
		
		MultipartFile file = organization.getAddressEmbeddable().getFile();
		
		if (file!=null && !file.isEmpty()) {
			organization.getAddressEmbeddable().setImage(file.getBytes());
		}
		
		else {
			
			Organization existingOrganization = findById(organization.getId());
			organization.getAddressEmbeddable().setImage(existingOrganization.getAddressEmbeddable().getImage());
		}
	
		
		organizationRepository.save(organization);
	}

	public List<Organization> findAll() {
		return organizationRepository.findAll();
	}

	public Organization findById(Long id) {
		return organizationRepository.findById(id).get();
	}

	public void deleteOrg(Long id) {
		organizationRepository.deleteById(id);
	}
}
