package com.eShoppingZone.order.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eShoppingZone.order.model.Order;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {

	List<Order> findByCustomerId(String orderId);

	List<Order> findByOrderDate(LocalDate localDate);

	Order findByOrderId(String orderId);

}
