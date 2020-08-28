package com.lti.resource;

import java.util.HashMap;
import java.util.Map;

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
import com.lti.model.Category;
import com.lti.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "category")
public class CategoryResource {
	CategoryService categoryService;
	
	public CategoryResource(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GET
	@Timed
	@ApiOperation(value = "Get all categories")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getCompanies() {
		return Response.ok(categoryService.getCategories()).build();
	}

	@GET
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get category by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getcategory(@PathParam("id") final int id) {
		return Response.ok(categoryService.getcategory(id)).build();
	}

	@POST
	@Timed
	@ApiOperation(value = "Get category by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response createcategory(@NotNull @Valid final Category category) {
		return Response.ok(categoryService.createcategory(category)).build();
	}

	@PUT
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get category by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response editcategory(@NotNull @Valid final Category category, @PathParam("id") final int id) {
		category.setId(id);
		return Response.ok(categoryService.editcategory(category)).build();
	}

	@DELETE
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get category by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response deletecategory(@PathParam("id") final int id) {
		Map<String, String> response = new HashMap<>();
		response.put("status", categoryService.deletecategory(id));
		return Response.ok(response).build();
	}
}
