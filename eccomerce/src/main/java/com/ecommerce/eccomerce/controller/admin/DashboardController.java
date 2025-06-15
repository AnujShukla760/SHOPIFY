package com.ecommerce.eccomerce.controller.admin;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.eccomerce.entity.ecom.Orders;
import com.ecommerce.eccomerce.enums.OrderStatus;
import com.ecommerce.eccomerce.service.CustomerService;
import com.ecommerce.eccomerce.service.UsersService;
import com.ecommerce.eccomerce.service.productService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController {

	private final CustomerService customerService;
	private final UsersService usersService;
	private final productService productService;

	@GetMapping("/showDashBoard")
	public String showDashBoard(Model model) {
		
		Integer userCount = usersService.userCount();
		Integer remainingStockCount = productService.remainingStockCount();
		

		Map<OrderStatus, Long> map = new EnumMap<>(OrderStatus.class);

		List<Object[]> countStatus = customerService.countOrderStatus();
		
		for (Object[] objects : countStatus) {
			
			OrderStatus orderStatus = (OrderStatus) objects[0];
			long statuscount= (long) objects[1];
			
			map.put(orderStatus, statuscount);
		}
		
		Long pendingOrder = map.getOrDefault(OrderStatus.PENDING, 0L);
		Long deliveredOrder = map.getOrDefault(OrderStatus.DELIVERED, 0L);
		
		model.addAttribute("pendingOrder", pendingOrder);
		model.addAttribute("deliveredOrder", deliveredOrder);
		model.addAttribute("userCount", userCount);
		model.addAttribute("remainingStockCount", remainingStockCount);
		return "admin/dashboard";
	}

	@GetMapping("/orderList")
	public String orderList(Model model) {
		List<Orders> orders = customerService.findAllWithProducts(); // create custom query with join fetch
		model.addAttribute("orders", orders);
		return "admin/orderList";
	}

	@PostMapping("/updateOrderStatus")
	public String updateOrderStatus(@RequestParam Long orderId, @RequestParam OrderStatus newStatus,
			@RequestParam(required = false) String statusChangeReason, RedirectAttributes redirectAttributes) {

		String message = customerService.updateOrderStatusAndReturnMessage(orderId, newStatus, statusChangeReason);
		redirectAttributes.addFlashAttribute("success", message);
		return "redirect:/admin/orderList";
	}
}
