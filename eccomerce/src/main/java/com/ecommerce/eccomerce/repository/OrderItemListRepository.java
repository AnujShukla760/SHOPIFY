package com.ecommerce.eccomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.eccomerce.entity.ecom.OrderItemList;

public interface OrderItemListRepository extends JpaRepository<OrderItemList, Long>{

}
