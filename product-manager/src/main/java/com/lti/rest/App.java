package com.lti.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lti.resource.CategoryResource;
import com.lti.resource.CompanyResource;
import com.lti.resource.OrderResource;
import com.lti.resource.ProductDetailResource;
import com.lti.resource.ProductResource;
import com.lti.service.CategoryService;
import com.lti.service.CompanyService;
import com.lti.service.OrderService;
import com.lti.service.ProductDetailService;
import com.lti.service.ProductService;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class App extends Application<AppConfiguration> {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	@Override
	public void initialize(Bootstrap<AppConfiguration> bootstrap) {
		bootstrap.addBundle(new SwaggerBundle<AppConfiguration>() {
			@Override
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
					final AppConfiguration appConfiguration) {
				return appConfiguration.getSwaggerBundleConfiguration();
			}
		});
	}

	@Override
	public void run(AppConfiguration c, Environment e) throws Exception {
		LOGGER.info("Product database configuration");
		final DataSource productDatasource = c.getProductDb().build(e.metrics(), "sql");
		DBI productDbi = new DBI(productDatasource);

		setupProductDb(productDatasource);
		setupOrderDb();

		LOGGER.info("Order database configuration");
		final DataSource orderDatasource = c.getOrderDb().build(e.metrics(), "sql");
		DBI orderDbi = new DBI(orderDatasource);

		LOGGER.info("Registering REST resources");
		e.jersey().register(new CompanyResource(productDbi.onDemand(CompanyService.class)));
		e.jersey().register(new CategoryResource(productDbi.onDemand(CategoryService.class)));
		e.jersey().register(new ProductResource(productDbi.onDemand(ProductService.class)));
		e.jersey()
				.register(new ProductDetailResource(productDbi.onDemand(ProductDetailService.class), orderDatasource));
		e.jersey().register(
				new OrderResource(orderDbi.onDemand(OrderService.class), productDbi.onDemand(ProductService.class)));
	}

	private void setupOrderDb() throws IOException, SQLException {
		LOGGER.info("In memory embeded database configurations");
		EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder().setPort(5433).start();

		try (Connection conn = embeddedPostgres.getPostgresDatabase().getConnection();
				Statement statement = conn.createStatement()) {
			statement.execute("create schema if not exists orderSchema");
			statement.execute("drop table if exists orderSchema.order_info");
			statement.execute(
					"create table if not exists orderSchema.order_info(id serial PRIMARY KEY, productId int, quantity int, status varchar(15), customerId int)");
			statement.execute(
					"insert into orderSchema.order_info (productId, quantity, status, customerId) values (1, 2, 'valid', 1), (2, 1, 'cancelled',5), (4, 4, 'delivered',100), (5, 2, 'valid', 1),"
							+ " (6, 19, 'valid', 1), (1, 2, 'valid', 1), (6, 19, 'valid', 1)");

		}
	}

	private void setupProductDb(DataSource productDatasource) throws SQLException {
		LOGGER.info("In memory embeded database configurations for product db");
		try (Connection conn = productDatasource.getConnection(); Statement statement = conn.createStatement();) {

			statement.execute("drop table if exists product");
			statement.execute("drop table if exists company");
			statement.execute("drop table if exists category");

			statement.execute(
					"create table if not exists company(id int PRIMARY KEY auto_increment, companyName varchar(20))");
			statement.execute(
					"create table if not exists category(id int PRIMARY KEY auto_increment, categoryName varchar(20))");
			statement.execute(
					"create table product(id int PRIMARY KEY auto_increment, name varchar(30), price number, discount number, stock int, colour varchar(20) ,"
							+ " description varchar(50), companyId int , categoryId int, FOREIGN KEY(companyId) REFERENCES company,  FOREIGN KEY(categoryId) REFERENCES category )");

			statement.execute(
					"insert into company (companyName) values ('APPLE'), ('SAMSUNG'), ('MI'), ('INTEL'), ('LENOVO'), ('LG')");
			statement.execute("insert into category (categoryName) values ('mobiles'), ('computers'), ('television')");
			statement.execute("insert into product (name, colour, description,price, discount, stock"
					+ ", companyId, categoryId) values ('AP1','black', 'Some description about AP1' ,70000, 13, 11, 1, 1),"
					+ " ('SP1','grey','Some description about SP1', 50000, 2, 2, 2, 1),"
					+ "('MP1','black','Some description about MP1', 20000, 9, 35, 3, 1),"
					+ "('IL1','grey','Some description about IL1', 67000, 0, 106, 4, 2),"
					+ "('IL2','black','Some description about IL2', 74000, 6, 300, 4, 2),"
					+ "('LL1','black','Some description about LL2', 80000, 10, 138, 5, 2),"
					+ "('LT1','black','Some description about LT1', 42500, 8, 62, 6, 3),"
					+ "('ST1','grey','Some description about ST1', 58360, 16, 168, 2, 3)");
		}
	}

	public static void main(String[] args) throws Exception {
		if(args.length > 0) {
			new App().run(args);
		} else {
			new App().run("server", "configuration.yml");
		}
		
	}
}
