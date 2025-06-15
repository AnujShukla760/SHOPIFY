package com.ecommerce.eccomerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.eccomerce.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	@Query("from Users where addressEmbeddable.email=:email and password=:password")
	Optional<Users> checkUser(String email, String password);

	Optional<Users> findByAddressEmbeddableEmail(String email);

	@Query("SELECT COUNT(s) FROM Users s")
	Integer userCount();

	@Modifying
	@Query("UPDATE Users u set u.password = :password where u.addressEmbeddable.email = :email")
	void updatePassword(String email, String password);

}
