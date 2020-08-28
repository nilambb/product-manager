package com.lti.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lti.model.ProductDetail;

public class ProductDetailMapper implements ResultSetMapper<ProductDetail>{

	@Override
	public ProductDetail map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		ProductDetail productDetail = new ProductDetail();
		productDetail.setId(rs.getInt("id"));
		productDetail.setCategoryName(rs.getString("categoryName"));
		productDetail.setName(rs.getString("name"));
		productDetail.setPrice(rs.getFloat("price"));
		productDetail.setDiscount(rs.getFloat("discount"));
		productDetail.setStock(rs.getInt("stock"));
		productDetail.setColour(rs.getString("colour"));
		productDetail.setDescription(rs.getString("description"));
		productDetail.setCompanyName(rs.getString("companyName"));
		return productDetail;
	}

}
