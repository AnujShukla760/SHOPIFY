package com.ecommerce.eccomerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.ecom.Tax;
import com.ecommerce.eccomerce.entity.ecom.TaxPercentage;
import com.ecommerce.eccomerce.service.TaxAndTaxPercentageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class TaxAndTaxPercentageController {

    private final TaxAndTaxPercentageService taxService;

    // ----------------------------- TAX SECTION ----------------------------------

    @GetMapping("/tax")
    public String taxForm(Model model) {
        model.addAttribute("tax", new Tax());
        model.addAttribute("taxList", taxService.findAllTaxes());
        return "admin/tax";
    }

    @PostMapping("/saveTax")
    public String saveTax(@ModelAttribute Tax tax, RedirectAttributes redirectAttributes) {
        taxService.saveTax(tax);
        redirectAttributes.addFlashAttribute("success", "Tax successfully added");
        return "redirect:/admin/tax";
    }

    @GetMapping("/updateTax")
    public String updateTax(@RequestParam("id") Long id, Model model) {
        Tax tax = taxService.findTaxById(id);
        model.addAttribute("tax", tax);
        model.addAttribute("taxList", taxService.findAllTaxes());
        return "admin/tax";
    }

    @GetMapping("/deleteTax")
    public String deleteTax(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        taxService.deleteTaxById(id);
        redirectAttributes.addFlashAttribute("warning", "Tax has been deleted!");
        return "redirect:/admin/tax";
    }

    // ----------------------------- TAX PERCENTAGE SECTION ----------------------------

    @GetMapping("/taxPercentage")
    public String taxPercentageForm(Model model) {
        model.addAttribute("taxPercentage", new TaxPercentage());
        model.addAttribute("taxPercentageList", taxService.findAllTaxPercentages());
        model.addAttribute("taxList", taxService.findAllTaxes()); // for dropdown
        return "admin/tax-percentage";
    }

    @PostMapping("/saveTaxPercentage")
    public String saveTaxPercentage(@ModelAttribute TaxPercentage taxPercentage, RedirectAttributes redirectAttributes) {
        taxService.saveTaxPercentage(taxPercentage);
        redirectAttributes.addFlashAttribute("success", "Tax Percentage successfully added");
        return "redirect:/admin/taxPercentage";
    }

    @GetMapping("/updateTaxPercentage")
    public String updateTaxPercentage(@RequestParam("id") Long id, Model model) {
        TaxPercentage taxPercentage = taxService.findTaxPercentageById(id);
        model.addAttribute("taxPercentage", taxPercentage);
        model.addAttribute("taxPercentageList", taxService.findAllTaxPercentages());
        model.addAttribute("taxList", taxService.findAllTaxes()); // for dropdown
        return "admin/tax-percentage";
    }

    @GetMapping("/deleteTaxPercentage")
    public String deleteTaxPercentage(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        taxService.deleteTaxPercentageById(id);
        redirectAttributes.addFlashAttribute("warning", "Tax Percentage has been deleted!");
        return "redirect:/admin/taxPercentage";
    }
}
