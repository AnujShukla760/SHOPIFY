package com.ecommerce.eccomerce.controller.admin;

import java.util.ArrayList;
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

import com.ecommerce.eccomerce.entity.Country;
import com.ecommerce.eccomerce.service.CountryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@GetMapping("/countryForm")
	public String countryForm(Model model, @RequestParam(required = false) String searchName) {

		System.out.println(searchName);

		Country country = new Country();
		model.addAttribute("country", country);

		List<Country> countries = new ArrayList<Country>();

		if (searchName != null) {
			countries = countryService.findByCountryName(searchName);
		} else {
			countries = countryService.findAllCountry();
		}

		model.addAttribute("countryList", countries);

		return "admin/country";
	}

	@PostMapping("/saveCountry")
	public String saveCountry(@ModelAttribute("country") Country country, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttribute) {

		countryService.saveCountry(country);
		redirectAttribute.addFlashAttribute("success", "Country Successfully Added");
		return "redirect:countryForm";
	}

	@GetMapping("/updateCountry")
	public String updateCountry(@RequestParam("id") Long id, Model model) {

		Country country = countryService.findCountryById(id);
		model.addAttribute("country", country);
		List<Country> countryList = countryService.findAllCountry();
		model.addAttribute("countryList", countryList);
		return "admin/country";
	}

	@GetMapping("/deleteCountry")
	public String deleteCountry(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttribute) {

		countryService.deleteCountryById(id);
		redirectAttribute.addFlashAttribute("warning", "Country has been deleted!");
		return "redirect:countryForm";
	}
}
