package com.lti.service;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lti.dao.ProductDao;
import com.lti.model.Product;

public abstract class ProductService {
	@CreateSqlObject
	abstract ProductDao productDao();
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
	private static final String SUCCESS = "Success";
	private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting employee.";
	private static final String PRODUCT_NOT_FOUND = "Product id %s not found.";
	
	public List<Product> getProducts() {
		return productDao().getProducts();
	}

	public Product getProduct(int id) {
		Product product = productDao().getProduct(id);
		if (Objects.isNull(product)) {
			throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, id), Status.NOT_FOUND);
		}
		return product;
	}

	public Product createProduct(Product product) {
		productDao().createProduct(product);
		return productDao().getProduct(productDao().lastInsertId());
	}

	public Product editProduct(Product product) {
		if (Objects.isNull(productDao().getProduct(product.getId()))) {
			throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, product.getId()), Status.NOT_FOUND);
		}
		productDao().editProduct(product);
		return productDao().getProduct(product.getId());
	}

	public String deleteProduct(final int id) {
		int result = productDao().deleteProduct(id);
		logger.info("Result in EmployeeService.deleteEmployee is: {}", result);
		switch (result) {
		case 1:
			return SUCCESS;
		case 0:
			throw new WebApplicationException(String.format(PRODUCT_NOT_FOUND, id), Status.NOT_FOUND);
		default:
			throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Status.INTERNAL_SERVER_ERROR);
		}
	}
}
