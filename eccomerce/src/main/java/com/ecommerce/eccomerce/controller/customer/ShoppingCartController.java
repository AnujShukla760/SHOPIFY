package com.ecommerce.eccomerce.controller.customer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.eccomerce.service.ShoppingCartService;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/shoppingCart")
@RequiredArgsConstructor
public class ShoppingCartController {

	private final ShoppingCartService cartService;

	@PostMapping("/addItemsToCart")
	@ResponseBody
	public ResponseEntity<?> addItemsToCart(@RequestParam Long productId, @RequestParam Integer quantity,
			HttpSession session) {

		int allCartCount = cartService.allCartCount(productId, quantity, session);
		return ResponseEntity.ok(allCartCount);

	}
	
	@PostMapping("/addItemsToCartForSingleProduct")
	@ResponseBody
	public ResponseEntity<?> addItemsToCartForSingleProduct(@RequestParam Long productId, @RequestParam Integer quantity,
	        HttpSession session) {
	    
	    String result = cartService.addOrUpdateCartItem(productId, quantity, session);
	    
	    Long cartId = SessionUtils.getSessionCartId(session);
	    int allCartCount = cartService.getAllProductCartCount(cartId, productId);  // total quantity in cart

	    Map<String, Object> response = new HashMap<>();
	    response.put("status", result);
	    response.put("count", allCartCount);

	    return ResponseEntity.ok(response);
	}

	@PostMapping("/removeQuantityToCart")
	@ResponseBody
	public ResponseEntity<?> removeQuantityToCart(@RequestParam Long productId, @RequestParam Integer quantity,
			HttpSession session) {
		
		int allCartCount = cartService.removeQuantityToCart(productId, quantity, session);
		return ResponseEntity.ok(allCartCount);
		
	}
	
	
	

}
