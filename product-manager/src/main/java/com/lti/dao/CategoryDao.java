package com.lti.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.lti.mapper.CategoryMapper;
import com.lti.model.Category;

@RegisterMapper(CategoryMapper.class)
public interface CategoryDao {
	@SqlQuery("select * from category;")
	List<Category> getCategories();
	
	@SqlQuery("select * from category where id = :id")
	Category getCategory(@Bind("id") int id);

	@SqlQuery("select last_insert_id();")
	int lastInsertId();

	@SqlUpdate("insert into category (categoryName) values (:categoryName)")
	void createCategory(@BindBean Category category);

	 @SqlUpdate("update category set categoryName = coalesce(:categoryName, categoryName) " +
	          " where id = :id")
	 void editCategory(@BindBean Category category);

	 @SqlUpdate("delete from category where id = :id")
	 int deleteCategory(@Bind("id") int id);
}
