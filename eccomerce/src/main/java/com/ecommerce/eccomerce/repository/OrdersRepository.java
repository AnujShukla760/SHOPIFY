package com.ecommerce.eccomerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.eccomerce.entity.ecom.Orders;
import com.ecommerce.eccomerce.enums.OrderStatus;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

	@Query("SELECT o FROM Orders o " 
			+ "LEFT JOIN FETCH o.orderItemLists ol "
			+ "LEFT JOIN FETCH ol.product "
			+ "where o.orderStatus= :pending")
	List<Orders> findByOrderStatus(OrderStatus pending);

	@Query("SELECT o FROM Orders o " 
			+ "LEFT JOIN FETCH o.orderItemLists ol " 
			+ "LEFT JOIN FETCH ol.product "
			+ "where o.id= :orderId")
	Orders findByOrderId(Long orderId);
	
	@Query("SELECT COUNT(o) FROM Orders o")
	Integer findOrderCount();

	@Modifying
	@Query("UPDATE Orders o SET o.orderStatus = :status, o.orderCancelReason = :reason WHERE o.id = :id")
	void updateOrderStatusAndReason(@Param("id") Long id, @Param("status") OrderStatus status,@Param("reason") String reason);

	@Query("SELECT o FROM Orders o "
			+ "LEFT JOIN FETCH o.orderItemLists i ")
	List<Orders> findAllWithProducts();

	@Query("SELECT COUNT(o) FROM Orders o where o.orderStatus = :pending")
	int countPendingOrderStatus(OrderStatus pending);
	
	@Query("SELECT COUNT(o) FROM Orders o where o.orderStatus = :comp")
	int countCompletedOrderStatus(OrderStatus comp);

	
	@Query("SELECT o.orderStatus , COUNT(o) FROM Orders o GROUP BY o.orderStatus")
	List<Object[]> countOrderStatus();

	
}
