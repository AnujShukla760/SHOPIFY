package com.ecommerce.eccomerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.eccomerce.entity.ecom.ProductCategory;
import com.ecommerce.eccomerce.entity.ecom.ProductSubCategory;
import com.ecommerce.eccomerce.repository.ProductCategoryRepository;
import com.ecommerce.eccomerce.repository.ProductSubCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductAndSubProductCategoryService {

	private final ProductCategoryRepository productCategoryRepository;
	private final ProductSubCategoryRepository productSubCategoryRepository;


	public List<ProductCategory> findAllProductCatList() {
		return productCategoryRepository.findAll();
	}

	public void saveProductCat(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
	}

	public ProductCategory findByProductCatId(Long id) {
		return productCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("product Category not found"));

	}

	public void deleteProductCatById(Long id) {
		productCategoryRepository.deleteById(id);
	}

//------------------------------------END OF PRODUCT CATEGORY :----------------------------------------------------
	

	
	public List<ProductSubCategory> findAllProductSubCatList() {
		return productSubCategoryRepository.findAll();
	}
	public void saveProductSubCategory(ProductSubCategory productSubCategory) {
	    MultipartFile file = productSubCategory.getFile();

	    if (file != null && !file.isEmpty()) {
	        try {
	            productSubCategory.setImage(file.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        if (productSubCategory.getId() != null) {
	            ProductSubCategory existingProductSubCategory = findByProductSubCatId(productSubCategory.getId());
	            if (existingProductSubCategory != null) {
	                productSubCategory.setImage(existingProductSubCategory.getImage());
	            }
	        }
	    }

	    productSubCategoryRepository.save(productSubCategory);
	}

	public ProductSubCategory findByProductSubCatId(Long id) {
		return productSubCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("product Sub Category not found"));
	}

	public void deleteProductSubCatById(Long id) {
		productSubCategoryRepository.deleteById(id);
	}
	
	
	public byte[] getImageBySubCategoryId(Long id) {
	    ProductSubCategory productSubCategory = findByProductSubCatId(id);

	    if (productSubCategory != null && productSubCategory.getImage() != null && productSubCategory.getImage().length > 0) {
	        return productSubCategory.getImage();
	    }
	    return new byte[0];
	}

	public List<ProductSubCategory> findByProductCategory(ProductCategory category) {
		return productSubCategoryRepository.findByProductCategory(category);
	}
	
	public List<ProductSubCategory> searchSubCategoriesByName(String keyword) {
        return productSubCategoryRepository.searchBySubCategoryName(keyword);
    }
	
	public List<ProductSubCategory> searchByCategoryAndName(ProductCategory category, String keyword) {
		return productSubCategoryRepository.searchByCategoryAndName(category , keyword);
	}

	public List<String> findSubCategoryNamesStartingWith(String term) {
    List<ProductSubCategory> matched = productSubCategoryRepository
            .findBySubCategoryNameStartingWithIgnoreCase(term);
    return matched.stream()
            .map(ProductSubCategory::getSubCategoryName)
            .distinct()
            .toList();
}


}
