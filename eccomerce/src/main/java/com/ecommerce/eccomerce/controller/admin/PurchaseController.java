package com.ecommerce.eccomerce.controller.admin;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.ecom.Purchase;
import com.ecommerce.eccomerce.entity.ecom.PurchaseItems;
import com.ecommerce.eccomerce.enums.PaymentMode;
import com.ecommerce.eccomerce.service.DistrictService;
import com.ecommerce.eccomerce.service.TaxAndTaxPercentageService;
import com.ecommerce.eccomerce.service.productService;
import com.ecommerce.eccomerce.service.purchaseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class PurchaseController {

	private final purchaseService purchaseService;
	private final DistrictService districtService;
	private final productService productService;
	private final TaxAndTaxPercentageService taxAndTaxPercentageService;

	@GetMapping("/purchaseForm")
	public String showPurchaseForm(Model model) {

		Purchase purchase = new Purchase();
		purchase.getPurchaseItems().add(new PurchaseItems());
		model.addAttribute("purchase", purchase);
		model.addAttribute("districtList", districtService.findAllDistrict());
		model.addAttribute("productList", productService.findAllProducts());
		model.addAttribute("taxPercentageList", taxAndTaxPercentageService.findAllTaxPercentages());
		model.addAttribute("paymentModes", PaymentMode.values());

		return "admin/purchase-form";
	}

	@PostMapping("/savePurchase")
	public String savePurchase(@ModelAttribute Purchase purchase, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttribute) {

		purchaseService.savePurchase(purchase);
		redirectAttribute.addFlashAttribute("success", "Purchase Successfully Added");
		return "redirect:purchaseForm";
	}

	@GetMapping("/purchaseManage")
	public String showPurchaseList(Model model) {
		model.addAttribute("purchases", purchaseService.findAllPurchase());
		return "admin/purchase-manage";
	}

	@GetMapping("/editPurchase")
	public String showEditPurchaseForm(@RequestParam Long id, Model model) {
		Purchase purchase = purchaseService.findByPurchaseId(id);
		if (purchase == null) {
			return "redirect:/admin/purchaseForm";
		}

		if (purchase.getPurchaseItems().size() == 0) {
			purchase.getPurchaseItems().add(new PurchaseItems());
		}

		model.addAttribute("purchase", purchase);
		model.addAttribute("districtList", districtService.findAllDistrict());
		model.addAttribute("productList", productService.findAllProducts());
		model.addAttribute("taxPercentageList", taxAndTaxPercentageService.findAllTaxPercentages());
		model.addAttribute("paymentModes", PaymentMode.values());
		return "admin/purchase-form";
	}

	@GetMapping("/purchaseView")
	public String purchaseView(@RequestParam Long id, Model model) {
		Purchase purchase = purchaseService.findByPurchaseId(id);
		if (purchase == null) {
			return "redirect:/admin/purchaseForm";
		}
		model.addAttribute("purchase", purchase);
		return "admin/purchase-view";
	}

	@GetMapping("/deletePurchase")
	public String deletePurchase(@RequestParam Long id, RedirectAttributes redirectAttribute) {
		purchaseService.deletePurchaseById(id);
		redirectAttribute.addFlashAttribute("warning", "Purchase has been deleted!");
		return "redirect:/admin/purchaseManage";
	}

	@GetMapping("/showVendorImage")
	@ResponseBody
	public byte[] showVendorImage(@RequestParam Long id) throws IOException {
		Purchase purchase = purchaseService.findByPurchaseId(id);

		if (purchase != null && purchase.getAddressEmbeddable().getImage().length != 0) {
			return purchase.getAddressEmbeddable().getImage();
		}

		else {
			return new byte[0];
		}
	}
}
