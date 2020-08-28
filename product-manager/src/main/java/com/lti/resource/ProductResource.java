package com.lti.resource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.lti.model.Product;
import com.lti.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "product")
public class ProductResource {
	ProductService productService;
	

	public ProductResource(ProductService productService) {
		this.productService = productService;
	}

	@GET
	@Timed
	@ApiOperation(value = "Get all products")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response getCompanies() {
		return Response.ok(productService.getProducts()).build();
	}

	@GET
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get Product by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response getProduct(@PathParam("id") final int id) {
		return Response.ok(productService.getProduct(id)).build();
	}

	@POST
	@Timed
	@ApiOperation(value = "Get Product by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response createProduct(@NotNull @Valid final Product product) {
		return Response.ok(productService.createProduct(product)).build();
	}

	@PUT
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get Product by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response editProduct(@NotNull @Valid final Product product, @PathParam("id") final int id) {
		product.setId(id);
		return Response.ok(productService.editProduct(product)).build();
	}

	@DELETE
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get Product by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response deleteProduct(@PathParam("id") final int id) {
		Map<String, String> response = new HashMap<>();
		response.put("status", productService.deleteProduct(id));
		return Response.ok(response).build();
	}
}
