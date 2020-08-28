package com.lti.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import com.lti.dao.ProductDetailDao;
import com.lti.model.ProductDetail;

public abstract class ProductDetailService {
	@CreateSqlObject
	abstract ProductDetailDao productDao();

	public List<ProductDetail> getallProductsAcrossCategory(Connection connection) throws SQLException {
		return processProductsAvailableStock(connection, productDao().getProductsAcrossAllCategory());
	}

	public List<ProductDetail> getProductsbyCategoryOrBetweenPrice(String category, float fromPrice, float toPrice,
			Connection connection) throws SQLException {
		return processProductsAvailableStock(connection,
				productDao().getProductsbyCategoryOrBetweenPrice(category, fromPrice, toPrice));
	}

	public List<ProductDetail> getProductsbyCategory(String category, Connection connection) throws SQLException {
		return processProductsAvailableStock(connection, productDao().getProductsbyCategory(category));
	}

	public List<ProductDetail> getProductsbyBetweenPrice(float fromPrice, float toPrice, Connection connection)
			throws SQLException {
		return processProductsAvailableStock(connection, productDao().getProductsbyBetweenPrice(fromPrice, toPrice));
	}

	public List<ProductDetail> getProductsbyCompanyOrBetweenPrice(String company, float fromPrice, float toPrice,
			Connection connection) throws SQLException {
		return processProductsAvailableStock(connection,
				productDao().getProductsbyCompanyOrBetweenPrice(company, fromPrice, toPrice));
	}

	private List<ProductDetail> processProductsAvailableStock(Connection orderDbConnection,
			List<ProductDetail> productList) throws SQLException {
		Map<Integer, ProductDetail> actualData = productList.stream()
				.collect(Collectors.toMap(ProductDetail::getId, prodctDetail -> prodctDetail));
		try (ResultSet rs = orderDbConnection.createStatement().executeQuery(
				"select productId, sum(id) as all_quantity from order_info where status = 'valid' group by productId")) {
			while (rs.next()) {
				int productId = rs.getInt("productId");
				int orderedQuantity = rs.getInt("all_quantity");
				if (actualData.containsKey(productId)) {
					ProductDetail product = actualData.get(productId);
					product.setAvailableStock(product.getStock() - orderedQuantity);
				}
			}
		};

		return actualData.values().stream().collect(Collectors.toList());

	}

	public ProductDetail getProductsbyId(int id) {
		return productDao().getProductsById(id);
	}
}
