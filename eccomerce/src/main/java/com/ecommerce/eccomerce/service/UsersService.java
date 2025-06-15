package com.ecommerce.eccomerce.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.eccomerce.entity.AddressEmbeddable;
import com.ecommerce.eccomerce.entity.District;
import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;
import com.ecommerce.eccomerce.entity.ecom.WishList;
import com.ecommerce.eccomerce.enums.UsersCategory;
import com.ecommerce.eccomerce.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;
	private final ShoppingCartService shoppingCartService;
	private final WishListService wishListService;
	private final DistrictService districtService;

	public void save(Users users) {
		boolean isNewUser = (users.getId() == null);

		if (isNewUser) {

			Integer userCount = userCount();
			if (userCount == 0) {
				// Set default category only for new users
				users.setUsersCategory(UsersCategory.ADMIN);
			} else {
				// Set default category only for new users
				users.setUsersCategory(UsersCategory.CUSTOMER);
			}

		} else {
			// Preserve existing category on update
			usersRepository.findById(users.getId()).ifPresent(existingUser -> {
				users.setUsersCategory(existingUser.getUsersCategory());
			});
		}

		// Save user
		Users savedUser = usersRepository.save(users);

		if (isNewUser) {
			// Create and save shopping cart
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUsers(savedUser);
			shoppingCartService.saveShoppingCart(shoppingCart);

			// Create and save wish list
			WishList wishList = new WishList();
			wishList.setUsers(savedUser);
			wishListService.saveWishList(wishList);
		}
	}

	public Users checkUser(String email, String password) {

		Optional<Users> checkUser = usersRepository.checkUser(email, password);

		if (checkUser.isPresent()) {
			return checkUser.get();
		}
		return null;
	}

	public Users findByEmail(String email) {

		Optional<Users> checkUser = usersRepository.findByAddressEmbeddableEmail(email);
		if (checkUser.isPresent()) {
			return checkUser.get();
		}
		return null;
	}

	public Users findById(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addSessionItems(HttpSession session, Users existingUser) {
		session.setAttribute("users", existingUser);
		session.setAttribute("cartId", existingUser.getShoppingCart().getId());
		session.setAttribute("wishListId", existingUser.getWishList().getId());
	}

	public void updateUserProfile(Users sessionUser, Users updatedUser, MultipartFile file) throws IOException {
		// Update basic user fields
		sessionUser.setFullName(updatedUser.getFullName());

		AddressEmbeddable updatedAddress = updatedUser.getAddressEmbeddable();
		AddressEmbeddable sessionAddress = sessionUser.getAddressEmbeddable();

		sessionAddress.setEmail(updatedAddress.getEmail());
		sessionAddress.setMobileNo(updatedAddress.getMobileNo());
		sessionAddress.setCity(updatedAddress.getCity());
		sessionAddress.setLandMark(updatedAddress.getLandMark());
		sessionAddress.setPincode(updatedAddress.getPincode());

		// Update District from ID
		if (updatedAddress.getDistrict() != null && updatedAddress.getDistrict().getId() != null) {
			District district = districtService.findDistrictById(updatedAddress.getDistrict().getId());
			sessionAddress.setDistrict(district);
		}

		// Profile Picture
		if (file != null && !file.isEmpty()) {
			byte[] imageBytes = file.getBytes();
			sessionAddress.setImage(imageBytes);
		}

		usersRepository.save(sessionUser);
	}

	public Integer userCount() {
		return usersRepository.userCount();
	}

	public List<Users> findAllUsers() {
		return usersRepository.findAll();
	}

	public String generateOtp() {
		SecureRandom secureRandom = new SecureRandom();
		int genrateOtp = 100000 + secureRandom.nextInt(900000);
		return String.valueOf(genrateOtp);
	}

	@Transactional
	public void updatePassword(String email, String password) {
		usersRepository.updatePassword(email, password);
	}
}
