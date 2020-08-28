package com.lti.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.lti.mapper.OrderMapper;
import com.lti.model.Order;

@RegisterMapper(OrderMapper.class)
public interface OrderDao {

	@SqlQuery("select * from order_info;")
	List<Order> getOrders();

	@SqlQuery("select * from order_info where id = :id")
	Order getOrder(@Bind("id") int id);

	@SqlUpdate("insert into order_info (productId, quantity) values (:productId, :quantity)")
	int createOrder(@BindBean Order order);

	@SqlUpdate("update order_info set productId = coalesce(:productId, productId), quantity = coalesce(:quantity, quantity), status = coalesce(:status, :status) " + " where id = :id")
	void editOrder(@BindBean Order orders);

	@SqlUpdate("delete from order_info where id = :id")
	int deleteOrder(@Bind("id") int id);

	@SqlQuery("select max(id) from order_info;")
	int lastInsertId();
	
	@SqlUpdate("DELETE FROM order_info a USING order_info b WHERE a.id < b.id AND a.productId = b.productId and a.quantity = b.quantity and a.customerId = b.customerId\r\n" + 
			"and a.status = 'valid' and b.status = 'valid';")
	void deleteDummyOrder();
}
