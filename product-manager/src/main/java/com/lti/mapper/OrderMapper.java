package com.lti.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lti.model.Order;

public class OrderMapper implements ResultSetMapper<Order> {
	private static final String ID = "id";
	private static final String PRODUCT_ID = "productId";
	private static final String QUANTITY = "quantity";
	private static final String STATUS = "status";

	@Override
	public Order map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getInt(ID));
		order.setProductId(resultSet.getInt(PRODUCT_ID));
		order.setQuantity(resultSet.getInt(QUANTITY));
		order.setStatus(resultSet.getString(STATUS));
		return order;
	}

}
