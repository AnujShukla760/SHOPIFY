package com.ecommerce.eccomerce.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.enums.UsersCategory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionCheckInterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String contextPath = request.getContextPath();
		String loginPage = contextPath + "/users/userLoginForm";
		String websiteUrl = contextPath + "/customer/showWebsite";
		
//		String requestURI = request.getRequestURI();
		String servletPath = request.getServletPath();
		
		if (servletPath.startsWith("/admin/public")) {
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("users") == null) {
			session = request.getSession(true);
			session.setAttribute("warning", "first you have to login");
			response.sendRedirect(loginPage);
			return false;
		}

		Users users = (Users) session.getAttribute("users");
		
		if (users == null || users.getUsersCategory() == null) {
			response.sendRedirect(loginPage);
			return false;
		}
		
		if (users.getUsersCategory() == UsersCategory.CUSTOMER  && servletPath.startsWith("/admin") && !servletPath.startsWith("/admin/public") ) {
			response.sendRedirect(websiteUrl);
		}
		
		return true;
	}
}
