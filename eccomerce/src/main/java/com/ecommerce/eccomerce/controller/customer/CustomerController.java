package com.ecommerce.eccomerce.controller.customer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.entity.ecom.CustomerReview;
import com.ecommerce.eccomerce.entity.ecom.Orders;
import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.ProductCategory;
import com.ecommerce.eccomerce.entity.ecom.ProductSubCategory;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCartList;
import com.ecommerce.eccomerce.entity.ecom.WishListItems;
import com.ecommerce.eccomerce.enums.OrderStatus;
import com.ecommerce.eccomerce.enums.PaymentMode;
import com.ecommerce.eccomerce.service.BrandService;
import com.ecommerce.eccomerce.service.CustomerService;
import com.ecommerce.eccomerce.service.DistrictService;
import com.ecommerce.eccomerce.service.ProductAndSubProductCategoryService;
import com.ecommerce.eccomerce.service.ShoppingCartService;
import com.ecommerce.eccomerce.service.WishListService;
import com.ecommerce.eccomerce.service.productService;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

	private final productService productService;
	private final ProductAndSubProductCategoryService productAndSubProductCategoryService;
	private final ShoppingCartService cartService;
	private final DistrictService districtService;
	private final CustomerService customerService;
	private final WishListService wishListService;
	private final BrandService brandService;

	@GetMapping("/subCategorySuggestions")
	@ResponseBody
	public List<String> getSubCategorySuggestions(@RequestParam("term") String term) {
		return productAndSubProductCategoryService.findSubCategoryNamesStartingWith(term);
	}

	@GetMapping("/showWebsite")
	public String showWebsite(Model model, @RequestParam(required = false) String searchInput) {

		Map<ProductCategory, List<ProductSubCategory>> map = new LinkedHashMap<>();
		List<ProductCategory> allProductCatList = productAndSubProductCategoryService.findAllProductCatList();

		List<ProductSubCategory> allProductSubCatList;

		if (searchInput != null && !searchInput.trim().isEmpty()) {
			// Get filtered subcategories
			allProductSubCatList = productAndSubProductCategoryService.searchSubCategoriesByName(searchInput);

			// Filter map to only include categories with matching subcategories
			for (ProductCategory productCategory : allProductCatList) {
				List<ProductSubCategory> filteredSubCats = allProductSubCatList.stream()
						.filter(sub -> sub.getProductCategory().getId().equals(productCategory.getId())).toList();

				if (!filteredSubCats.isEmpty()) {
					map.put(productCategory, filteredSubCats);
				}
			}

			model.addAttribute("searchInput", searchInput);

		} else {
			// Default - show all
			allProductSubCatList = productAndSubProductCategoryService.findAllProductSubCatList();

			for (ProductCategory productCategory : allProductCatList) {
				List<ProductSubCategory> listSubCat = productAndSubProductCategoryService
						.findByProductCategory(productCategory);
				map.put(productCategory, listSubCat);
			}
		}

		model.addAttribute("productList", productService.findAllProducts());
		model.addAttribute("productSubCatList", allProductSubCatList);
		model.addAttribute("map", map);
		model.addAttribute("search", "search");

		return "customer-website";
	}

	@GetMapping("/customerCart")
	public String customerCart(Model model, HttpSession session) {

		Long cartId = SessionUtils.getSessionCartId(session);

		ShoppingCart cart = cartService.findllShoppingCartItems(cartId);

		List<ShoppingCartList> shoppingCartLists = cart.getShoppingCartLists();
		model.addAttribute("shoppingCartLists", shoppingCartLists);

		double totalCartPrice = 0;

		for (ShoppingCartList shoppingCartList : shoppingCartLists) {

			double mrp = shoppingCartList.getProduct().getMrp();
			int quantity = shoppingCartList.getQuantity();
			totalCartPrice += mrp * quantity;
		}

		model.addAttribute("totalCartPrice", totalCartPrice);
		model.addAttribute("totalTaxPrice", customerService.totalTax(session));
		model.addAttribute("totalMrpWithTax", customerService.totalPriceWithTax(session));

		return "customer/customer-cart";
	}

	@GetMapping("/showProductByCategory")
	public String showProductByCategory(Model model, @RequestParam(required = false) Long id) {

		List<Product> productList = productService.findAllByproductSubCategoryId(id);
		model.addAttribute("productList", productList);
		model.addAttribute("brandList", brandService.findAllBrand());
		model.addAttribute("search", "search");
		return "customer/show-all-products";
	}

	@GetMapping("/customerWishList")
	public String customerWishList(Model model, HttpSession session) {
		List<WishListItems> wishListItems = customerService.wishListItems(session);
		model.addAttribute("wishListItems", wishListItems);

		model.addAttribute("wishListCount",
				wishListService.getAllWishListCount(SessionUtils.getSessionWishListId(session)));
		return "customer/customer-wishlist";
	}

	@GetMapping("/customerCheckOut")
	public String customerCheckOut(Model model, HttpSession session) {

		Long cartId = SessionUtils.getSessionCartId(session);

		ShoppingCart cart = cartService.findllShoppingCartItems(cartId);

		List<ShoppingCartList> shoppingCartLists = cart.getShoppingCartLists();
		model.addAttribute("shoppingCartLists", shoppingCartLists);

		double subTotal = 0;

		for (ShoppingCartList shoppingCartList : shoppingCartLists) {

			double mrp = shoppingCartList.getProduct().getMrp();
			int quantity = shoppingCartList.getQuantity();
			subTotal += mrp * quantity;
		}

		model.addAttribute("subTotal", subTotal);
		model.addAttribute("totalTaxPrice", customerService.totalTax(session));
		model.addAttribute("totalMrpWithTax", customerService.totalPriceWithTax(session));
		model.addAttribute("orders", new Orders());
		model.addAttribute("districtList", districtService.findAllDistrict());
		model.addAttribute("paymentModeList", PaymentMode.values());
		return "customer/customer-checkout";
	}

	@PostMapping("/saveOrders")
	public String saveOrders(@ModelAttribute Orders orders, Model model, RedirectAttributes attributes,
			HttpSession session) {

		customerService.saveOrder(orders, session);
		attributes.addFlashAttribute("success", "Order Successfully Placed");
		return "redirect:/customer/customerOrder";

	}

	@GetMapping("/productView")
	public String productView(Model model, @RequestParam(required = false) Long id) {

		Product product = productService.findByProductId(id);
		model.addAttribute("product", product);
		model.addAttribute("customerReview", new CustomerReview());
		model.addAttribute("productReviewList", customerService.findAllProductReview(id));
		return "customer/product-view";
	}

	@PostMapping("/customerReview")
	public String customerReview(@ModelAttribute CustomerReview customerReview,
			@RequestParam(required = false) Long productId, @RequestParam(required = false) int rating,
			RedirectAttributes attributes, HttpSession session) {

		customerService.saveCustomerReview(customerReview, productId, rating, session);
		attributes.addFlashAttribute("success", "Customer Review Successfully Added");
		return "redirect:/customer/productView?id=" + productId;
	}

	@GetMapping("/customerOrderAjax")
	public String customerOrderAjax(Model model, @RequestParam(required = false) OrderStatus statusValue) {

		List<Orders> orders = customerService.findByOrderStatus(statusValue);
		if (orders != null && !orders.isEmpty()) {
			model.addAttribute("orders", orders);
		}
		model.addAttribute("statusValue", statusValue.toString());
		return "customer/customer-order-ajax";
	}

	@GetMapping("/customerOrder")
	public String customerOrder(Model model, @RequestParam(required = false) OrderStatus statusValue) {
		return "customer/customer-order";
	}

	@GetMapping("/customerOrderView")
	public String customerOrderView(Model model, @RequestParam(required = false) Long id) {
		Orders orders = customerService.findByOrderId(id);
		model.addAttribute("order", orders);
		return "customer/customer-order-view";
	}

	@PostMapping("/customerOrderCancel")
	public String customerOrderCancel(@RequestParam Long id, @RequestParam String cancelReason, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {

		Orders orders = customerService.findByOrderId(id);

		if (orders.getOrderStatus() != OrderStatus.PENDING) {
			throw new RuntimeException("You are not allowed to cancel the order.");
		}

		customerService.cancelOrder(id, cancelReason);
		redirectAttributes.addFlashAttribute("success", "Order cancelled successfully.");
		return "redirect:/customer/customerOrder";
	}

	@GetMapping("/customerProfile")
	public String customerProfile(Model model, HttpSession session) {
		Users sessionUser = SessionUtils.getSessionUser(session);
		model.addAttribute("sessionUser", sessionUser);
		return "customer/customer-profile";
	}

	@GetMapping("/customerProfileUpdate")
	public String customerProfileUpdate(Model model, HttpSession session) {
		Users sessionUser = SessionUtils.getSessionUser(session);
		model.addAttribute("sessionUser", sessionUser);
		model.addAttribute("districts", districtService.findAllDistrict()); // Fetch all districts
		return "customer/customer-profile-update";
	}

	@GetMapping("/removeItemfromCart")
	public ResponseEntity<?> removeItemfromCart(@RequestParam Long productId, HttpSession session) {
		cartService.removeItemFromCart(productId, session);
		return ResponseEntity.ok("Product removed from the cart");

	}

	@GetMapping("/removeItemfromWishList")
	public ResponseEntity<?> removeItemfromWishList(@RequestParam Long productId, HttpSession session) {
		wishListService.removeItemFromWishList(productId, session);
		return ResponseEntity.ok("Product removed from the WishList");

	}
}
