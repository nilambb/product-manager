package com.lti.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.lti.mapper.ProductDetailMapper;
import com.lti.model.ProductDetail;

@RegisterMapper(ProductDetailMapper.class)
public interface ProductDetailDao {
	
	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id ;")
	List<ProductDetail> getProductsAcrossAllCategory();

	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id where"
			+ " c.categoryName = :categoryName or (price >= :gtPrice  and price <= :ltPrice) ;")
	List<ProductDetail> getProductsbyCategoryOrBetweenPrice(@Bind("categoryName") String categoryName, @Bind("gtPrice") float gtPrice,
			@Bind("ltPrice") float ltPrice);
	
	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id where"
			+ " c.categoryName = :categoryName or (price >= :gtPrice  and price <= :ltPrice) or com.companyName = :companyName;")
	List<ProductDetail> getProductsbyCompanyOrCategoryOrBetweenPrice(@Bind("categoryName") String categoryName, @Bind("gtPrice") float gtPrice,
			@Bind("ltPrice") float ltPrice);
	
	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id where"
			+ " c.categoryName = :categoryName")
	List<ProductDetail> getProductsbyCategory(@Bind("categoryName") String categoryName);

	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id where"
			+ " price >= :gtPrice  and price <= :ltPrice")
	List<ProductDetail> getProductsbyBetweenPrice(@Bind("gtPrice")  float gtPrice, @Bind("ltPrice")  float ltPrice);
	
	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id where"
			+ " com.companyName = :companyName or (price >= :gtPrice  and price <= :ltPrice) ;")
	List<ProductDetail> getProductsbyCompanyOrBetweenPrice(@Bind("companyName") String companyName, @Bind("gtPrice")  float gtPrice,@Bind("ltPrice") float ltPrice);

	@SqlQuery("select * from product p inner join category c on p.categoryId = c.id inner join company com on p.companyId = com.id where"
			+ " com.companyName = :companyName;")
	List<ProductDetail> getProductsbyCompany(@Bind("companyName") String companyName);
	
	@SqlQuery("select * from product where id = :id;")
	ProductDetail getProductsById(@Bind("id") int id);

}
