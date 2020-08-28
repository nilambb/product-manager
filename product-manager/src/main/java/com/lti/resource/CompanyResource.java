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
import com.lti.model.Company;
import com.lti.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/company")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "company")
public class CompanyResource {
	private CompanyService companyService;

	public CompanyResource(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GET
	@Timed
	@ApiOperation(value = "Get all companies")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getCompanies() {
		return Response.ok(companyService.getCompanies()).build();
	}

	@GET
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get company by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response getCompany(@PathParam("id") final int id) {
		return Response.ok(companyService.getCompany(id)).build();
	}

	@POST
	@Timed
	@ApiOperation(value = "Get company by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response createCompany(@NotNull @Valid final Company company) {
		return Response.ok(companyService.createCompany(company)).build();
	}

	@PUT
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get company by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response editCompany(@NotNull @Valid final Company company, @PathParam("id") final int id) {
		company.setId(id);
		return Response.ok(companyService.editCompany(company)).build();
	}

	@DELETE
	@Timed
	@Path("{id}")
	@ApiOperation(value = "Get company by id")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
            @ApiResponse(code = 404, message = "Name not found"),
            @ApiResponse(code = 405, message = "Validation exception") })
	public Response deleteCompany(@PathParam("id") final int id) {
		Map<String, String> response = new HashMap<>();
		response.put("status", companyService.deleteCompany(id));
		return Response.ok(response).build();
	}

}
