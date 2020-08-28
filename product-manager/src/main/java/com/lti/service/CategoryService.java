package com.lti.service;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lti.dao.CategoryDao;
import com.lti.model.Category;

public abstract class CategoryService {
	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
	private static final String SUCCESS = "Success";
	private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting employee.";
	private static final String CATEGORY_NOT_FOUND = "Category id %s not found.";
	
	@CreateSqlObject
	abstract CategoryDao categoryDao();

	public List<Category> getCategories() {
		return categoryDao().getCategories();
	}

	public Category getcategory(int id) {
		Category category = categoryDao().getCategory(id);
		if (Objects.isNull(category)) {
			throw new WebApplicationException(String.format(CATEGORY_NOT_FOUND, id), Status.NOT_FOUND);
		}
		return category;
	}

	public Category createcategory(Category category) {
		categoryDao().createCategory(category);
		return categoryDao().getCategory(categoryDao().lastInsertId());
	}

	public Category editcategory(Category category) {
		if (Objects.isNull(categoryDao().getCategory(category.getId()))) {
			throw new WebApplicationException(String.format(CATEGORY_NOT_FOUND, category.getId()), Status.NOT_FOUND);
		}
		categoryDao().editCategory(category);
		return categoryDao().getCategory(category.getId());
	}

	public String deletecategory(final int id) {
		int result = categoryDao().deleteCategory(id);
		logger.info("Result in EmployeeService.deleteEmployee is: {}", result);
		switch (result) {
		case 1:
			return SUCCESS;
		case 0:
			throw new WebApplicationException(String.format(CATEGORY_NOT_FOUND, id), Status.NOT_FOUND);
		default:
			throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Status.INTERNAL_SERVER_ERROR);
		}
	}
}
