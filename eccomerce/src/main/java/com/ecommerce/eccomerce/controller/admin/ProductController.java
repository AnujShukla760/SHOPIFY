package com.ecommerce.eccomerce.controller.admin;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
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

import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.ProductImages;
import com.ecommerce.eccomerce.service.BrandService;
import com.ecommerce.eccomerce.service.ProductAndSubProductCategoryService;
import com.ecommerce.eccomerce.service.TaxAndTaxPercentageService;
import com.ecommerce.eccomerce.service.productService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ProductController {

	private final productService productService;
	private final TaxAndTaxPercentageService taxAndTaxPercentageService;
	private final ProductAndSubProductCategoryService productAndSubProductCategoryService;
	private final BrandService brandService;
	
	@GetMapping("/productForm")
	public String showPurchaseForm(Model model) {
		model.addAttribute("taxPercentage", taxAndTaxPercentageService.findAllTaxPercentages());
		model.addAttribute("productSubCatList", productAndSubProductCategoryService.findAllProductSubCatList());
		model.addAttribute("brands", brandService.findAllBrand());

		Product product = new Product();
		product.getProductImages().add(new ProductImages());
		model.addAttribute("product", product);
		return "admin/product-form";
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttribute) {

		productService.saveProduct(product);
		redirectAttribute.addFlashAttribute("success", "Product Successfully Added");
		return "redirect:productForm";
	}

	@GetMapping("/productManage")
	public String showProductList(Model model) {
		model.addAttribute("products", productService.findAllProducts());
		return "admin/product-manage";
	}

	@GetMapping("/editProduct")
	public String showEditProductForm(@RequestParam Long id, Model model) {
		Product product = productService.findByProductId(id);
		if (product == null) {
			return "redirect:/admin/productForm";
		}

		System.out.println("size : " + product.getProductImages().size());

		if (product.getProductImages().size() == 0) {

			ProductImages productImages = new ProductImages();
			product.getProductImages().add(productImages);

		}

		model.addAttribute("product", product);
		model.addAttribute("taxPercentage", taxAndTaxPercentageService.findAllTaxPercentages());
		model.addAttribute("productSubCatList", productAndSubProductCategoryService.findAllProductSubCatList());
		model.addAttribute("brands", brandService.findAllBrand());
		return "admin/product-form";
	}

	@GetMapping("/productView")
	public String productView(@RequestParam Long id, Model model) {
		Product product = productService.findByProductId(id);
		if (product == null) {
			return "redirect:/admin/productForm";
		}
		
		String formattedDescription = product.getDescription()
			    .replace("\r\n", "<br/>")
			    .replace("\n", "<br/>");
			model.addAttribute("productDescription", formattedDescription);


		
		model.addAttribute("product", product);
		return "admin/product-view";
	}

	@GetMapping("/public/showProductImage")
	@ResponseBody
	public byte[] showImage(@RequestParam Long imageId) throws IOException {
		ProductImages img = productService.findByProductImageId(imageId);

		if (img != null && img.getProductImage() != null && img.getProductImage().length > 0) {
			return img.getProductImage();
		}

		// Return default image if not found
		ClassPathResource defaultImage = new ClassPathResource("static/img/fav.png");
		try (InputStream in = defaultImage.getInputStream()) {
			return in.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttribute) {
		productService.deleteProductById(id);
		redirectAttribute.addFlashAttribute("warning", "Product has been deleted!");
		return "redirect:/admin/productManage";
	}

}
