package com.ecommerce.eccomerce.controller.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.eccomerce.service.WishListService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {

	private final WishListService listService;

	@PostMapping("/addItemsToWishList")
	@ResponseBody
	public ResponseEntity<?> addItemsToWisList(@RequestParam Long productId, HttpSession session) {

		int allWishListCount = listService.allWishListCount(productId, session);
		return ResponseEntity.ok(allWishListCount);

	}

}
