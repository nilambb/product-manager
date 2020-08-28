package com.lti.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.lti.mapper.ProductMapper;
import com.lti.model.Product;

@RegisterMapper(ProductMapper.class)
public interface ProductDao {
	@SqlQuery("select * from product;")
	public List<Product> getProducts();

	@SqlQuery("select * from product where id = :id")
	public Product getProduct(@Bind("id") int id);

	@SqlQuery("select last_insert_id();")
	public int lastInsertId();

	@SqlUpdate("insert into product (name, price, discount, stock, colour, description, companyId, categoryId) "
			+ "values (:name, :price, :discount, :stock, :colour, :description, :companyId, :categoryId)")
	public void createProduct(@BindBean Product product);

	@SqlUpdate("update product set name = coalesce(:name, name), price = coalesce(:price, price), discount = coalesce(:discount, discount), "
			+ " stock = coalesce(:stock, stock), colour = coalesce(:colour, colour), description = coalesce(:description, description), "
			+ " companyId = coalesce(:companyId, companyId), categoryId = coalesce(:categoryId, categoryId)" + " where id = :id")
	public void editProduct(@BindBean Product product);

	@SqlUpdate("delete from product where id = :id")
	public int deleteProduct(@Bind("id") int id);
}
