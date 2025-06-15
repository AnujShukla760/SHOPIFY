package com.ecommerce.eccomerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.ecom.Brand;
import com.ecommerce.eccomerce.service.BrandService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class BrandController {

	private final BrandService brandService;

	@GetMapping("/brandForm")
	public String countryForm(Model model, @RequestParam(required = false) String searchName) {
		model.addAttribute("brandList", brandService.findAllBrand());
		model.addAttribute("brand", new Brand());
		return "admin/brand";
	}

	@PostMapping("/saveBrand")
	public String saveCountry(@ModelAttribute Brand brand, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttribute) {

		brandService.saveBrand(brand);
		redirectAttribute.addFlashAttribute("success", "Brand Successfully Added");
		return "redirect:brandForm";
	}

	@GetMapping("/updateBrand")
	public String updateCountry(@RequestParam("id") Long id, Model model) {

		Brand brand = brandService.findBrandById(id);
		model.addAttribute("brand", brand);
		model.addAttribute("brandList", brandService.findAllBrand());
		return "admin/brand";
	}

	@GetMapping("/deleteBrand")
	public String deleteBrand(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttribute) {

		brandService.deleteBrandById(id);
		redirectAttribute.addFlashAttribute("warning", "Brand has been deleted!");
		return "redirect:brandForm";
	}
}
