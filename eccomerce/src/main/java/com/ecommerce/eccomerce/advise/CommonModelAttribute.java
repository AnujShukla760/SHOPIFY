package com.ecommerce.eccomerce.advise;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ecommerce.eccomerce.entity.Organization;
import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.service.OrganizationService;
import com.ecommerce.eccomerce.service.ShoppingCartService;
import com.ecommerce.eccomerce.service.WishListService;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonModelAttribute {

	private final OrganizationService organizationService;
	private final ShoppingCartService cartService;
	private final WishListService wishListService;

	@ModelAttribute
	public void showAllModel(Model model, HttpSession session) {

		Users users = (Users) session.getAttribute("users");
		if (users != null) {
			model.addAttribute("loggedUser", users);

			Long cartId = SessionUtils.getSessionCartId(session);
			if (cartId != null) {
				Integer allCartCount = cartService.getAllCartCount(cartId);
				model.addAttribute("allCartCount", allCartCount);
			}

			Long wishListId = SessionUtils.getSessionWishListId(session);

			if (wishListId != null) {
				Integer allWishListCount = wishListService.getAllWishListCount(wishListId);
				model.addAttribute("allWishListCount", allWishListCount);

			}
		}

		List<Organization> list = organizationService.findAll();
		if (list != null && !list.isEmpty()) {
			model.addAttribute("organization", list.get(0));
		} else {
			model.addAttribute("organization", new Organization());
		}
	}
}
