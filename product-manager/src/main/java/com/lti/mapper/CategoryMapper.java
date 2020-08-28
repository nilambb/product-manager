package com.lti.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lti.model.Category;

public class CategoryMapper implements ResultSetMapper<Category> {
	private static final String ID = "id";
	private static final String NAME = "categoryName";

	@Override
	public Category map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		Category category = new Category();
		category.setId(resultSet.getInt(ID));
		category.setCategoryName(resultSet.getString(NAME));
		return category;
	}
}
