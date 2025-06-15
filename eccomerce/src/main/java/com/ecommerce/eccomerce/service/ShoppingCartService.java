package com.ecommerce.eccomerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCartList;
import com.ecommerce.eccomerce.repository.ShoppingCartListRepository;
import com.ecommerce.eccomerce.repository.ShoppingCartRepository;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

	private final ShoppingCartRepository shoppingCartRepository;
	private final ShoppingCartListRepository shoppingCartListRepository;
	private final productService productService;

	public void saveShoppingCart(ShoppingCart shoppingCart) {
		shoppingCartRepository.save(shoppingCart);
	}

	public Optional<ShoppingCart> findByShoppingCartId(Long cartId) {
		return shoppingCartRepository.findById(cartId);

	}

	public void saveShoppingCartList(ShoppingCartList shoppingCartList) {
		shoppingCartListRepository.save(shoppingCartList);
	}

	public Optional<ShoppingCartList> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product) {
		return shoppingCartListRepository.findByShoppingCartAndProduct(shoppingCart, product);
	}

	public Integer getAllCartCount(Long cartId) {
		return shoppingCartListRepository.getAllCartCount(cartId);
	}
	
	
	public int allCartCount(Long productId, Integer quantity, HttpSession session) {

		Long cartId = SessionUtils.getSessionCartId(session);
		Optional<ShoppingCart> optionalCart = findByShoppingCartId(cartId);

		Product product = productService.findByProductId(productId);

		Optional<ShoppingCartList> optionalCarts = findByShoppingCartAndProduct(optionalCart.get(), product);

		if (optionalCarts.isPresent()) {

			ShoppingCartList existingShoppingCartList = optionalCarts.get();
			existingShoppingCartList.setQuantity(existingShoppingCartList.getQuantity() + quantity);
			saveShoppingCartList(existingShoppingCartList);

		} else {

			ShoppingCartList shoppingCartList = new ShoppingCartList();
			shoppingCartList.setShoppingCart(optionalCart.get());
			shoppingCartList.setProduct(product);
			shoppingCartList.setQuantity(quantity);
			saveShoppingCartList(shoppingCartList);
		}

		int allCartCount = getAllCartCount(cartId);
		return allCartCount;

	}

	public String addOrUpdateCartItem(Long productId, Integer quantity, HttpSession session) {

	    Long cartId = SessionUtils.getSessionCartId(session);
	    Optional<ShoppingCart> optionalCart = findByShoppingCartId(cartId);

	    Product product = productService.findByProductId(productId);

	    Optional<ShoppingCartList> optionalCarts = findByShoppingCartAndProduct(optionalCart.get(), product);

	    if (!optionalCarts.isPresent()) {
	        // Item not in cart yet, add new
	        ShoppingCartList shoppingCartList = new ShoppingCartList();
	        shoppingCartList.setShoppingCart(optionalCart.get());
	        shoppingCartList.setProduct(product);
	        shoppingCartList.setQuantity(quantity);
	        saveShoppingCartList(shoppingCartList);

	        return "added";  // New item added
	    } else {
	        // Item already in cart, no addition done
	        return "exists";  // Already present
	    }
	}

	public int getAllProductCartCount(Long cartId , Long productId) {
		return shoppingCartListRepository.getAllProductCartCount(cartId , productId);
	}

	public ShoppingCart findllShoppingCartItems(Long cartId) {
		return shoppingCartRepository.findllShoppingCartItems(cartId);
	}

	public List<ShoppingCartList> findByShoppingCart(ShoppingCart shoppingCart) {
		return shoppingCartListRepository.findByShoppingCart(shoppingCart);
	}

	public void removeItemFromCart(Long productId, HttpSession session) {

		Long cartId = SessionUtils.getSessionCartId(session);
		Optional<ShoppingCart> optionalCart = findByShoppingCartId(cartId);

		Product product = productService.findByProductId(productId);

		if (product == null) {
			throw new RuntimeException("Product Not found");
		}

		if (optionalCart.isPresent()) {

			ShoppingCart shoppingCart = optionalCart.get();

			Optional<ShoppingCartList> optionalCartList = shoppingCartListRepository .findByShoppingCartAndProduct(shoppingCart, product);

			if (optionalCartList.isPresent()) {
				ShoppingCartList shoppingCartList = optionalCartList.get();
				shoppingCartListRepository.delete(shoppingCartList);
			}
		}
	}

	public int removeQuantityToCart(Long productId, Integer quantity, HttpSession session) {

		Long cartId = SessionUtils.getSessionCartId(session);
		Optional<ShoppingCart> optionalCart = findByShoppingCartId(cartId);

		Product product = productService.findByProductId(productId);

		Optional<ShoppingCartList> optionalCarts = findByShoppingCartAndProduct(optionalCart.get(), product);

		if (optionalCarts.isPresent()) {

			ShoppingCartList existingShoppingCartList = optionalCarts.get();
			existingShoppingCartList.setQuantity(existingShoppingCartList.getQuantity() - quantity);
			saveShoppingCartList(existingShoppingCartList);

		}

		int allCartCount = getAllCartCount(cartId);
		return allCartCount;

	}

	@SuppressWarnings("deprecation")
	public void removeAllItemsFromCustomerCart(Long cartId) {
		ShoppingCart cart = shoppingCartRepository.findllShoppingCartItems(cartId);
		shoppingCartListRepository.deleteInBatch(cart.getShoppingCartLists());
	}
}
