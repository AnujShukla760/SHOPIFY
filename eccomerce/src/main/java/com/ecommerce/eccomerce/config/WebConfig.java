package com.ecommerce.eccomerce.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final SessionCheckInterceptor sessionCheckInterceptor;

	private List<String> excludeList = List.of
		  ( "/users/userLoginForm", 
			"/users/userRegister", 
			"/users/saveUserRegister",
			"/users/checkUserLogin",
			"/customer/subCategorySuggestions",
			"/customer/showWebsite", 
			"/customer/showProductByCategory", 
			"/customer/productView", 
			"/users/forgetPassword", // password reset :
			"/users/verifyOtp",               
			"/users/resetPassword", 
			"/css/**",
			"/img/**",
			"/js/**",
			"/website/**"
		  );

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionCheckInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns(excludeList);

	}

}
