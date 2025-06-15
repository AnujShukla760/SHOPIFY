package com.ecommerce.eccomerce.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.District;
import com.ecommerce.eccomerce.service.CountryService;
import com.ecommerce.eccomerce.service.DistrictService;
import com.ecommerce.eccomerce.service.StateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DistrictController {

	private final DistrictService districtService;
	private final StateService stateService;
	private final CountryService countryService;

	@GetMapping("/districtForm")
	public String DistrictForm(Model model) {

		District district = new District();
		model.addAttribute("district", district);

		List<District> allDistrict = districtService.findAllDistrict();
		model.addAttribute("allDistrict", allDistrict);

		model.addAttribute("countries", countryService.findAllCountry());

		return "admin/district";
	}

	@PostMapping("/saveDistrict")
	public String saveDistrict(@ModelAttribute("district") District district, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttribute) {

		districtService.saveDistrict(district);
		redirectAttribute.addFlashAttribute("success", "District saved successfully");
		return "redirect:districtForm";
	}

	@GetMapping("/editDistrict")
	public String editDistrict(@RequestParam("id") Long id, Model model) {
		District district = districtService.findDistrictById(id);
		model.addAttribute("district", district);

		List<District> allDistrict = districtService.findAllDistrict();
		model.addAttribute("allDistrict", allDistrict);
		model.addAttribute("stateList", stateService.findStatesByCountryId(district.getState().getCountry().getId()));
		model.addAttribute("countries", countryService.findAllCountry());

		return "admin/district";

	}

	@GetMapping("/deleteDistrict")
	public String deleteDistrict(@RequestParam("id") Long id, RedirectAttributes redirectAttribute) {
		districtService.deleteDistrictById(id);
		redirectAttribute.addFlashAttribute("warning", "District has been deleted!");
		return "redirect:districtForm";

	}

	@GetMapping("/getDistrictsByStateId")
	public String getDistrictsByStateId(@RequestParam("stateId") Long stateId, Model model) {
		
		List<District> districts = districtService.findAllDistrictsByStateId(stateId);
		model.addAttribute("districts", districts);
		model.addAttribute("district", "districtId");
		return "admin/ajax/allAjax";
	}

}
