package com.ecommerce.eccomerce.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.Organization;
import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.service.EmailService;
import com.ecommerce.eccomerce.service.OtpVerificationService;
import com.ecommerce.eccomerce.service.UsersService;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;
	private final EmailService emailService;
	private final OtpVerificationService otpVerificationService;

	@GetMapping("/userRegister")
	public String userRegisterForm(Model model) {
		model.addAttribute("users", new Users());
		return "userRegister";
	}

	@PostMapping("/saveUserRegister")
	public String saveUser(@ModelAttribute Users users, RedirectAttributes attributes, Model model) {

		Users existingUser = usersService.findByEmail(users.getAddressEmbeddable().getEmail());

		if (existingUser != null) {
			attributes.addFlashAttribute("warning", "User Already Exist");
			return "redirect:/users/userRegister";
		}

		usersService.save(users);
		attributes.addFlashAttribute("success", "User Registered Successfully");
		return "redirect:/users/userRegister";
	}

	@GetMapping("/userLoginForm")
	public String userLoginForm(Model model, HttpSession session) {
		model.addAttribute("users", new Users());
		return "userLoginForm";
	}

	@PostMapping("/checkUserLogin")
	public String checkUserLogin(@ModelAttribute Users users, RedirectAttributes attributes, Model model,
			HttpSession session) {

		Users existingUser = usersService.checkUser(users.getAddressEmbeddable().getEmail(), users.getPassword());

		if (existingUser != null) {
			usersService.addSessionItems(session, existingUser);
			return "redirect:/admin/showDashBoard";
		} else {
			attributes.addFlashAttribute("warning", "First you have to register yourself");
			return "redirect:/users/userLoginForm";

		}
	}

	@GetMapping("/logout")
	public String logoutUser(RedirectAttributes attributes, HttpSession session) {

		session.removeAttribute("users");
		attributes.addFlashAttribute("success", "You have been logged out successfully");
		return "redirect:/users/userLoginForm";
	}

	@GetMapping("/hi")
	public String hi(Model model, HttpSession session) {
		model.addAttribute("organization", new Organization());
		return "hi";
	}

	@GetMapping("/hi1")
	public String hi1(Model model, HttpSession session) {
		return "hi1";
	}

	@GetMapping("/public/showUserImage")
	@ResponseBody
	public byte[] showImage(@RequestParam Long id) throws IOException {
		Users users = usersService.findById(id);

		if (users != null && users.getAddressEmbeddable().getImage() != null
				&& users.getAddressEmbeddable().getImage().length > 0) {
			return users.getAddressEmbeddable().getImage();
		}

		// Return default image if not found
		ClassPathResource defaultImage = new ClassPathResource("static/img/fav.png");
		try (InputStream in = defaultImage.getInputStream()) {
			return in.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute("sessionUser") Users updatedUser,
			@RequestParam("addressEmbeddable.file") MultipartFile file, HttpSession session,
			RedirectAttributes redirectAttributes) {

		Users sessionUser = SessionUtils.getSessionUser(session);

		try {
			usersService.updateUserProfile(sessionUser, updatedUser, file);
			session.setAttribute("sessionUser", sessionUser);
			redirectAttributes.addFlashAttribute("success", "Profile updated successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
			return "redirect:/customerProfileUpdate";
		}

		return "redirect:/customer/customerProfile";
	}

	@GetMapping("/usersList")
	public String usersList(Model model) {
		model.addAttribute("usersList", usersService.findAllUsers());
		return "admin/users-list";
	}

	// forget password :

	@GetMapping("/forgetPassword")
	public String handleForgetForm(Model model) {
		return "forget-password";
	}

	@PostMapping("/forgetPassword")
	public String forgetPassword(Model model, @RequestParam(required = false) String regsiteredEmail,
			RedirectAttributes attributes) {

		String sendOtp = usersService.generateOtp();
		emailService.sendOtp(regsiteredEmail, sendOtp);
		otpVerificationService.saveVerification(regsiteredEmail , sendOtp);
		attributes.addFlashAttribute("success", "OTP Successfully sent to : " + regsiteredEmail);
		return "redirect:verifyOtp?email=" + regsiteredEmail;
	}

	// Verify OTP :

	@GetMapping("/verifyOtp")
	public String handleOtpForm(Model model, @RequestParam(required = false) String email) {
		model.addAttribute("email", email);
		return "verify-otp";
	}

	@PostMapping("/verifyOtp")
	public String verifyOtp(Model model, @RequestParam(required = false) String email, @RequestParam(required = false) String insertOtp,
			RedirectAttributes attributes) {
		
		boolean validateOtp = otpVerificationService.validateOtp(email , insertOtp);
		System.out.println(validateOtp);

		if (!validateOtp) {
	        attributes.addFlashAttribute("warning", "OTP does not match, please try again.");
	        return "redirect:verifyOtp?email=" + email;
	    }

	    attributes.addFlashAttribute("success", "OTP verified, you may proceed.");
	    return "redirect:resetPassword?email=" + email;
	}

	// Reset Password :

	@GetMapping("/resetPassword")
	public String handleResetPasswordForm(Model model, @RequestParam(required = false) String email) {
		model.addAttribute("email", email);
		return "reset-password";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(Model model, @RequestParam(required = false) String email,
			@RequestParam(required = false) String password, @RequestParam(required = false) String confirmPassword,
			RedirectAttributes attributes) {

		if (!password.equals(confirmPassword)) {
			attributes.addFlashAttribute("warning", "password and confirm password do not match...");
			return "redirect:resetPassword?email=" + email;
		}

		usersService.updatePassword(email, password);
		attributes.addFlashAttribute("success", "password successfully updated ....");
		return "redirect:userLoginForm";
	}

}
