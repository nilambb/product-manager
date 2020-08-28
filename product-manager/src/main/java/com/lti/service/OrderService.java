package com.lti.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lti.dao.OrderDao;
import com.lti.model.Order;

public abstract class OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	private static final String ORDER_NOT_FOUND = "Order not found";

	@CreateSqlObject
	abstract OrderDao orderDao();

	public List<Order> getOrders() {
		return orderDao().getOrders();
	}

	public Order getOrder(int id) {
		Order company = orderDao().getOrder(id);
		if (Objects.isNull(company)) {
			throw new WebApplicationException(String.format(ORDER_NOT_FOUND, id), Status.NOT_FOUND);
		}
		return company;
	}

	public String createOrder(Order order) {
		int result = orderDao().createOrder(order);

		// return orderDao().getOrder(orderDao().lastInsertId());
		switch (result) {
		case 1:
			return "success";
		case 0:
			throw new WebApplicationException("Order can not be added", Status.NOT_FOUND);
		default:
			throw new WebApplicationException("Unexpected delete error", Status.INTERNAL_SERVER_ERROR);
		}
	}

	public Order editOrder(Order order) {
		if (Objects.isNull(orderDao().getOrder(order.getId()))) {
			throw new WebApplicationException(String.format(ORDER_NOT_FOUND, order.getId()), Status.NOT_FOUND);
		}
		orderDao().editOrder(order);
		return orderDao().getOrder(order.getId());
	}

	public String deleteOrder(final int id) {
		int result = orderDao().deleteOrder(id);
		logger.info("Result in EmployeeService.deleteEmployee is: {}", result);
		switch (result) {
		case 1:
			return "success";
		case 0:
			throw new WebApplicationException(String.format(ORDER_NOT_FOUND, id), Status.NOT_FOUND);
		default:
			throw new WebApplicationException("Unexpected delete error", Status.INTERNAL_SERVER_ERROR);
		}
	}

	public void deleteDummyOrder() {
		orderDao().deleteDummyOrder();

	}

}
