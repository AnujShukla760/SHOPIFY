package com.ecommerce.eccomerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.ProductImages;
import com.ecommerce.eccomerce.repository.ProductImagesRepository;
import com.ecommerce.eccomerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class productService {

	private final ProductRepository productRepository;
	private final ProductImagesRepository productImagesRepository;

	public void saveProduct(Product product) {

		List<ProductImages> productImages = product.getProductImages();

		for (ProductImages productImages2 : productImages) {

			if (productImages2.getId() == null) {

				// insert case :

				if (productImages2.getFile() != null && !productImages2.getFile().isEmpty()) {

					try {
						productImages2.setProductImage(productImages2.getFile().getBytes());
						productImages2.setProduct(product);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

			else {

				// update case

				ProductImages existingProductImg = productImagesRepository.findById(productImages2.getId()).get();

				if (productImages2.getFile() != null && !productImages2.getFile().isEmpty()) {

					try {
						productImages2.setProductImage(productImages2.getFile().getBytes());
						productImages2.setProduct(product);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				else {
					productImages2.setProductImage(existingProductImg.getProductImage());
				}

			}

		}

		productRepository.save(product);
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Product findByProductId(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Tax not found"));
	}

	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}

	public ProductImages findByProductImageId(Long imageId) {
		return productImagesRepository.findById(imageId).orElse(null);
	}

	public List<Product> findAllByproductSubCategoryId(Long id) {
		return productRepository.findAllByproductSubCategoryId(id);
	}

	@Transactional
	public void reduceStock(int quantity, Product product) {
		productRepository.reduceStock(quantity, product.getId());
	}

	public Integer remainingStockCount() {
		return productRepository.remainingStockCount();
	}
}
