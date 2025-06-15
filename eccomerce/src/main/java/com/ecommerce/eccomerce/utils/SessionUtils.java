package com.ecommerce.eccomerce.utils;

import com.ecommerce.eccomerce.entity.Users;

import jakarta.servlet.http.HttpSession;

public class SessionUtils {
	
	public static Users getSessionUser(HttpSession session) {
		return (Users) session.getAttribute("users");
	}
	
	public static Long getSessionCartId(HttpSession session) {
		return (Long) session.getAttribute("cartId");
	}
	
	public static Long getSessionWishListId(HttpSession session) {
		return (Long) session.getAttribute("wishListId");
	}

}
