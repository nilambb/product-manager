package com.lti.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lti.model.Product;

public class ProductMapper implements ResultSetMapper<Product> {

	@Override
	public Product map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		Product product = new Product();
		product.setCategoryId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getFloat("price"));
		product.setDiscount(rs.getFloat("discount"));
		product.setStock(rs.getInt("stock"));
		product.setColour(rs.getString("colour"));
		product.setDescription(rs.getString("description"));
		product.setCompanyId(rs.getInt("companyId"));
		product.setCategoryId(rs.getInt("categoryId"));
		return product;
	}

}
