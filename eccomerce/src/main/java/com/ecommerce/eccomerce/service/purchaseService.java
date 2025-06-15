package com.ecommerce.eccomerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.eccomerce.entity.ecom.Purchase;
import com.ecommerce.eccomerce.entity.ecom.PurchaseItems;
import com.ecommerce.eccomerce.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class purchaseService {

	private final PurchaseRepository purchaseRepository;

	public void savePurchase(Purchase purchase) {

		// bill image upload code :
		MultipartFile billFile = purchase.getBillFile();

		if (billFile != null && !billFile.isEmpty()) {
			try {
				purchase.setBillImage(billFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (purchase.getId() != null) {
			Purchase existingPurchase = findByPurchaseId(purchase.getId());
			purchase.setBillImage(existingPurchase.getBillImage());
		}

		// vendor image code :
		MultipartFile vendorImageFile = purchase.getAddressEmbeddable().getFile();

		if (vendorImageFile != null && !vendorImageFile.isEmpty()) {
			try {
				purchase.getAddressEmbeddable().setImage(vendorImageFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (purchase.getId() != null) {
			Purchase existingPurchase = findByPurchaseId(purchase.getId());
			purchase.getAddressEmbeddable().setImage(existingPurchase.getAddressEmbeddable().getImage());
		}

		// set the relationship
		List<PurchaseItems> purchaseItems = purchase.getPurchaseItems();
		for (PurchaseItems item : purchaseItems) {
			item.setPurchase(purchase);
		}

		purchaseRepository.save(purchase);
	}

	public List<Purchase> findAllPurchase() {
		return purchaseRepository.findAll();
	}

	public Purchase findByPurchaseId(Long id) {
		return purchaseRepository.findById(id).orElse(null);
	}

	public void deletePurchaseById(Long id) {
		purchaseRepository.deleteById(id);
	}

}
