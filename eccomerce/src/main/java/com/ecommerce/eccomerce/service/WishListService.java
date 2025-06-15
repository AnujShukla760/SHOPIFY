package com.ecommerce.eccomerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.WishList;
import com.ecommerce.eccomerce.entity.ecom.WishListItems;
import com.ecommerce.eccomerce.repository.WishListItemsRepository;
import com.ecommerce.eccomerce.repository.WishListRepository;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListService {

	private final WishListRepository wishListRepository;
	private final productService productService;
	private final WishListItemsRepository wishListItemsRepository;

	public void saveWishList(WishList wishList) {
		wishListRepository.save(wishList);
	}

	//
	public int allWishListCount(Long productId, HttpSession session) {

		Long wishListId = SessionUtils.getSessionWishListId(session);

		System.out.println("wishListId   " + wishListId);

		Optional<WishList> optionalWishList = findByWishListId(wishListId);

		if (optionalWishList.isEmpty()) {
			throw new RuntimeException("Wish List not found");
		}

		Product product = productService.findByProductId(productId);

		if (product == null) {
			throw new RuntimeException("Product Not Found");
		}

		Optional<WishListItems> optionalWishListItems = findByWishListAndProduct(optionalWishList.get(), product);

		if (optionalWishListItems.isEmpty()) {

			WishList wishList = optionalWishList.get();

			WishListItems wishListItems = new WishListItems();
			wishListItems.setProduct(product);
			wishListItems.setWishList(wishList);
			wishListItems.setQuantity(1);
			
			wishListItemsRepository.save(wishListItems);
		}

		int allWishListCount = getAllWishListCount(optionalWishList.get().getId());

		System.out.println("return     : " + allWishListCount);

		return allWishListCount;
	}

	public Integer getAllWishListCount(Long wishListId) {
		return wishListItemsRepository.getAllWishListCount(wishListId);
	}

	public Optional<WishListItems> findByWishListAndProduct(WishList wishList, Product product) {
		return wishListItemsRepository.findByWishListAndProduct(wishList, product);
	}

	public Optional<WishList> findByWishListId(Long cartId) {
		return wishListRepository.findById(cartId);
	}

	public WishList findllWishListItems(Long wishListId) {
		return wishListRepository.findllWishListItems(wishListId);
	}
	
	public void removeItemFromWishList(Long productId, HttpSession session) {

		Long wishListId = SessionUtils.getSessionWishListId(session);
		Optional<WishList> optionalwishList = findByWishListId(wishListId);

		Product product = productService.findByProductId(productId);

		if (product == null) {
			throw new RuntimeException("Product Not found");
		}

		if (optionalwishList.isPresent()) {

			WishList wishList = optionalwishList.get();

			Optional<WishListItems> optionalWishList  = wishListItemsRepository.findByWishListAndProduct(wishList, product);

			if (optionalWishList.isPresent()) {
				WishListItems wiListItems = optionalWishList.get();
				wishListItemsRepository.delete(wiListItems);
			}
		}
	}

}
