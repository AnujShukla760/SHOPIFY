package com.ecommerce.eccomerce.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.Organization;
import com.ecommerce.eccomerce.service.DistrictService;
import com.ecommerce.eccomerce.service.OrganizationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class OrganizationController {

	private final OrganizationService organizationService;
	private final DistrictService districtService;

	@GetMapping("/organizationForm")
	public String organizationForm(Model model) {

		model.addAttribute("organization", new Organization());
		model.addAttribute("districtList", districtService.findAllDistrict());
		model.addAttribute("orgList", organizationService.findAll());
		return "admin/organization";
	}

	@PostMapping("/saveOrganization")
	public String saveOrganization(@ModelAttribute Organization organization, RedirectAttributes attributes,
			Model model) throws IOException {

		System.out.println(organization.getAddressEmbeddable().getFile().getBytes());

		organizationService.save(organization);
		attributes.addFlashAttribute("success", "Organization Successfully Added");
		return "redirect:/admin/organizationForm";
	}

	@GetMapping("/updateOrg")
	public String updateOrg(@RequestParam("id") Long id, Model model) {

		Organization organization = organizationService.findById(id);
		model.addAttribute("organization", organization);
		
		
		List<Organization> list = new ArrayList<Organization>();
		model.addAttribute("districtList", districtService.findAllDistrict());
		model.addAttribute("orgList", list);

		return "admin/organization";
	}

	@GetMapping("/deleteOrg")
	public String deleteOrg(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttribute) {

		organizationService.deleteOrg(id);
		redirectAttribute.addFlashAttribute("warning", "Organization has been deleted!");
		return "redirect:organizationForm";
	}

	@GetMapping("/public/showImage")
	@ResponseBody
	public byte[] showImage(@RequestParam Long id) throws IOException {
	    Organization organization = organizationService.findById(id);

	    if (organization != null && organization.getAddressEmbeddable().getImage().length != 0) {
	        return organization.getAddressEmbeddable().getImage();
	    } 
	    
	    else {
	        // Use Spring's ClassPathResource to load a static image
	        ClassPathResource imgFile = new ClassPathResource("static/img/fav.png");
	        
	        try (InputStream in = imgFile.getInputStream()) {
	            return in.readAllBytes();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new byte[0];
	        }
	    }
	}

}