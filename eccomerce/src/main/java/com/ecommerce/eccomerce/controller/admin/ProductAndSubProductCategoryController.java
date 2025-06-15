package com.ecommerce.eccomerce.controller.admin;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.ecom.ProductCategory;
import com.ecommerce.eccomerce.entity.ecom.ProductSubCategory;
import com.ecommerce.eccomerce.service.ProductAndSubProductCategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ProductAndSubProductCategoryController {

	private final ProductAndSubProductCategoryService productCatAndSubCatService;

	@GetMapping("/productCategory")
	public String countryForm(Model model, @RequestParam(required = false) String searchName) {

		model.addAttribute("productCatList", productCatAndSubCatService.findAllProductCatList());
		model.addAttribute("productCategory", new ProductCategory());
		return "admin/product-category";
	}

	@PostMapping("/saveProductCategory")
	public String saveProductCategory(@ModelAttribute ProductCategory productCategory, Model model,
			RedirectAttributes redirectAttribute) {

		productCatAndSubCatService.saveProductCat(productCategory);
		redirectAttribute.addFlashAttribute("success", "Product Category Successfully Added");
		return "redirect:productCategory";
	}

	@GetMapping("/updateProductCategory")
	public String updateProductCategory(@RequestParam("id") Long id, Model model) {

		ProductCategory productCategory = productCatAndSubCatService.findByProductCatId(id);
		model.addAttribute("productCategory", productCategory);
		return "admin/product-category";
	}

	@GetMapping("/deleteProductCategory")
	public String deleteProductCategory(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttribute) {

		productCatAndSubCatService.deleteProductCatById(id);
		redirectAttribute.addFlashAttribute("warning", "Product Category has been deleted!");
		return "redirect:productCategory";
	}
	
//--------------------------------------- END PRODUCT CATEGORY SECTION :-------------------------------------------------
	
	@GetMapping("/productSubCategory")
    public String subCategoryForm(Model model) {
        model.addAttribute("productSubCategory", new ProductSubCategory());
        model.addAttribute("productSubCatList", productCatAndSubCatService.findAllProductSubCatList());
        model.addAttribute("productCatList", productCatAndSubCatService.findAllProductCatList()); // for dropdown
        return "admin/product-sub-category";
    }

    @PostMapping("/saveProductSubCategory")
    public String saveProductSubCategory(@ModelAttribute ProductSubCategory productSubCategory,RedirectAttributes redirectAttribute) {
    	productCatAndSubCatService.saveProductSubCategory(productSubCategory);
        redirectAttribute.addFlashAttribute("success", "Product Sub-Category Successfully Added");
        return "redirect:/admin/productSubCategory";
    }

    @GetMapping("/updateProductSubCategory")
    public String updateProductSubCategory(@RequestParam("id") Long id, Model model) {
        ProductSubCategory subCategory = productCatAndSubCatService.findByProductSubCatId(id);
        model.addAttribute("productSubCategory", subCategory);
        model.addAttribute("productSubCatList", productCatAndSubCatService.findAllProductSubCatList());
        model.addAttribute("productCatList", productCatAndSubCatService.findAllProductCatList()); // for dropdown
        return "admin/product-sub-category";
    }

    @GetMapping("/deleteProductSubCategory")
    public String deleteProductSubCategory(@RequestParam("id") Long id, RedirectAttributes redirectAttribute) {
    	productCatAndSubCatService.deleteProductSubCatById(id);
        redirectAttribute.addFlashAttribute("warning", "Product Sub-Category has been deleted!");
        return "redirect:/admin/productSubCategory";
    }
    
    @GetMapping("/public/showCatImg")
	@ResponseBody
	public byte[] showCatImg(@RequestParam Long id) throws IOException {
	   return productCatAndSubCatService.getImageBySubCategoryId(id);
	}
}
