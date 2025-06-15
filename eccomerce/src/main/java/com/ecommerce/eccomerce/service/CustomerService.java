package com.ecommerce.eccomerce.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.eccomerce.entity.Users;
import com.ecommerce.eccomerce.entity.ecom.CustomerReview;
import com.ecommerce.eccomerce.entity.ecom.OrderItemList;
import com.ecommerce.eccomerce.entity.ecom.Orders;
import com.ecommerce.eccomerce.entity.ecom.Product;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCart;
import com.ecommerce.eccomerce.entity.ecom.ShoppingCartList;
import com.ecommerce.eccomerce.entity.ecom.WishList;
import com.ecommerce.eccomerce.entity.ecom.WishListItems;
import com.ecommerce.eccomerce.enums.OrderStatus;
import com.ecommerce.eccomerce.repository.CustomerReviewRepository;
import com.ecommerce.eccomerce.repository.OrdersRepository;
import com.ecommerce.eccomerce.utils.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final ShoppingCartService cartService;
	private final OrdersRepository ordersRepository;
	private final productService productService;
	private final WishListService wishListService;
	private final CustomerReviewRepository customerReviewRepository;
	private final EmailService emailService;

	@Transactional
	public void saveOrder(Orders orders, HttpSession session) {
		Users sessionUser = SessionUtils.getSessionUser(session);

		orders.setUsers(sessionUser);
		orders.setOrderStatus(OrderStatus.PENDING);
		orders.setOrderDateTime(LocalDateTime.now());
		orders.setTotalPrice(totalPriceWithTax(session));

		List<OrderItemList> orderItemLists = new ArrayList<>();

		List<ShoppingCartList> shoppingCartLists = shoppingCartLists(session);

		for (ShoppingCartList shoppingCartList : shoppingCartLists) {
			Product product = shoppingCartList.getProduct();
			int requestedQty = shoppingCartList.getQuantity();
			int availableStock = product.getQuantity(); // current stock

			// Validate requested quantity
			if (requestedQty <= 0) {
				throw new IllegalArgumentException(
						"Invalid quantity requested for product: " + product.getProductname());
			}

			// Check stock availability
			if (availableStock <= 0) {
				throw new RuntimeException("Product " + product.getProductname() + " is out of stock.");
			}

			if (requestedQty > availableStock) {
				throw new RuntimeException("Requested quantity (" + requestedQty + ") for product "
						+ product.getProductname() + " exceeds available stock (" + availableStock + ").");
			}

			OrderItemList orderItem = new OrderItemList();
			orderItem.setOrders(orders);
			orderItem.setProduct(product);
			orderItem.setQuantity(requestedQty);
			orderItemLists.add(orderItem);

			// Reduce stock: new stock = current stock - requested quantity
			int newStock = availableStock - requestedQty;
			productService.reduceStock(newStock, product);
		}

		Long cartId = SessionUtils.getSessionCartId(session);
		orders.setOrderItemLists(orderItemLists);
		ordersRepository.save(orders);
		cartService.removeAllItemsFromCustomerCart(cartId);

		// Optionally send email to customer
		emailService.sendEmailToCustomer(orders.getAddressEmbeddable().getEmail());
	}

	public Double totalPriceWithTax(HttpSession session) {

		List<ShoppingCartList> shoppingCartLists = shoppingCartLists(session);
		double totalCartPrice = 0;
		double totalTaxPrice = 0;

		for (ShoppingCartList shoppingCartList : shoppingCartLists) {

			double mrp = shoppingCartList.getProduct().getMrp();
			int quantity = shoppingCartList.getQuantity();
			double taxRate = shoppingCartList.getProduct().getTaxPercentage().getTaxPercentage();

			totalCartPrice += mrp * quantity;
			totalTaxPrice += (mrp * quantity) * (taxRate / 100);
		}

		double totalMrpWithTax = totalCartPrice + totalTaxPrice;
		return totalMrpWithTax;
	}

	public Double totalTax(HttpSession session) {

		List<ShoppingCartList> shoppingCartLists = shoppingCartLists(session);
		double totalTaxPrice = 0;

		for (ShoppingCartList shoppingCartList : shoppingCartLists) {

			double mrp = shoppingCartList.getProduct().getMrp();
			int quantity = shoppingCartList.getQuantity();
			double taxRate = shoppingCartList.getProduct().getTaxPercentage().getTaxPercentage();
			totalTaxPrice += (mrp * quantity) * (taxRate / 100);
		}

		return totalTaxPrice;
	}

	public List<ShoppingCartList> shoppingCartLists(HttpSession session) {
		Long cartId = SessionUtils.getSessionCartId(session);
		ShoppingCart cart = cartService.findllShoppingCartItems(cartId);
		return cart.getShoppingCartLists();
	}

	public List<WishListItems> wishListItems(HttpSession session) {
		Long wishListId = SessionUtils.getSessionWishListId(session);
		WishList wishList = wishListService.findllWishListItems(wishListId);
		return wishList.getWishListItems();
	}

	public List<Orders> findByOrderStatus(OrderStatus pending) {
		return ordersRepository.findByOrderStatus(pending);
	}

	public Orders findByOrderId(Long id) {
		return ordersRepository.findByOrderId(id);
	}

	@Transactional
	public void cancelOrder(Long id, String reason) {
		ordersRepository.updateOrderStatusAndReason(id, OrderStatus.CANCELLED, reason);
	}

	public Integer findOrderCount() {
		return ordersRepository.findOrderCount();
	}

	public List<Orders> findAllWithProducts() {
		return ordersRepository.findAllWithProducts();
	}

	@Transactional
	public String updateOrderStatusAndReturnMessage(Long orderId, OrderStatus newStatus, String statusChangeReason) {
		ordersRepository.updateOrderStatusAndReason(orderId, newStatus, statusChangeReason);

		switch (newStatus) {
		case DELIVERED:
			return "Order marked as Delivered successfully.";
		case ONGOING:
			return "Order marked as Ongoing.";
		case CANCELLED:
			return "Order cancelled successfully.";
		default:
			return "Order status updated.";
		}
	}

	public void saveCustomerReview(CustomerReview customerReview, Long productId, int rating, HttpSession session) {

		Product product = productService.findByProductId(productId);
		Users sessionUser = SessionUtils.getSessionUser(session);

		customerReview.setProduct(product);
		customerReview.setRating(rating);
		customerReview.setUsers(sessionUser);

		if (customerReview.getFile() != null && !customerReview.getFile().isEmpty()) {
			try {
				customerReview.setImage(customerReview.getFile().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		customerReviewRepository.save(customerReview);
	}

	public List<CustomerReview> findAllProductReview(Long id) {
		return customerReviewRepository.findByProductId(id);
	}

	public int countPendingOrderStatus(OrderStatus pending) {
		return ordersRepository.countPendingOrderStatus(pending);
	}

	public int countCompletedOrderStatus(OrderStatus pending) {
		return ordersRepository.countCompletedOrderStatus(pending);
	}

	public List<Object[]> countOrderStatus() {
		return ordersRepository.countOrderStatus();
	}
}
