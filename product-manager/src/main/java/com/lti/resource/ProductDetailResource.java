package com.lti.resource;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import com.codahale.metrics.annotation.Timed;
import com.lti.model.ProductDetail;
import com.lti.service.ProductDetailService;
import com.lti.utils.ProductUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/productDetails")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "productDetails")
public class ProductDetailResource {
	ProductDetailService productDetailService;
	DataSource orderDatasource;
	
	public ProductDetailResource(ProductDetailService productDetailService, DataSource orderDatasource) {
		this.productDetailService = productDetailService;
		this.orderDatasource = orderDatasource;;
	}

	@GET
	@Path("v1")
	@Timed
	@ApiOperation(value = "Get all products across all categories")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getProductsAcrossCatogory() throws SQLException {
		return Response.ok(productDetailService.getallProductsAcrossCategory(orderDatasource.getConnection())).build();
	}

	@GET
	@Path("v1/query/product")
	@Timed
	@ApiOperation(value = "Get all products across all categories")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getProductsByCategory(@DefaultValue("") @QueryParam("category") final String category,
			@DefaultValue("-1") @QueryParam("gtPrice") final float gtPrice,
			@DefaultValue("-1") @QueryParam("ltPrice") final float ltPrice,
			@DefaultValue("") @QueryParam("company") final String company) throws SQLException {
	
		if (StringUtils.isNoneBlank(category) && gtPrice > 0 && ltPrice > 0) {
			return Response.ok(
					productDetailService.getProductsbyCategoryOrBetweenPrice(category.toLowerCase(), gtPrice, ltPrice, orderDatasource.getConnection()))
					.build();
		} else if(StringUtils.isNoneBlank(company) && gtPrice > 0 && ltPrice > 0) {
			return Response.ok(
					productDetailService.getProductsbyCompanyOrBetweenPrice(company.toUpperCase(), gtPrice, ltPrice, orderDatasource.getConnection()))
					.build();
		}
		else if (StringUtils.isNoneBlank(category)) {
			return Response.ok(
					productDetailService.getProductsbyCategoryOrBetweenPrice(category.toLowerCase(), gtPrice, ltPrice, orderDatasource.getConnection()))
					.build();
		} else if (gtPrice > 0 && ltPrice > 0) {
			return Response.ok(productDetailService.getProductsbyCategoryOrBetweenPrice(category, gtPrice, ltPrice, orderDatasource.getConnection()))
					.build();
		} else {
			throw new WebApplicationException("Passed invalid query parameters", Status.NOT_FOUND);
		}
		
	}
	
	@GET
	@Path("v1/{category}")
	@Timed
	@ApiOperation(value = "Get all products across all categories")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getProductsByCategoryOrBetweenPrice(@DefaultValue("") @PathParam("category") final String category) throws SQLException {
		List<ProductDetail> productDetailsList = productDetailService.getProductsbyCategory(category, orderDatasource.getConnection());
		if(null != productDetailsList && !productDetailsList.isEmpty()) {
			productDetailsList.stream().forEach(ProductUtil::getDiscountedPriceofProduct);
		}
		return Response.ok(productDetailsList).build();
	}
}
