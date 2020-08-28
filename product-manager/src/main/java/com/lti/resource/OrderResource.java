package com.lti.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;
import com.lti.model.Order;
import com.lti.model.Product;
import com.lti.service.OrderService;
import com.lti.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "order")
public class OrderResource {

	private OrderService orderService;
	private ProductService productService;

	public OrderResource(OrderService orderService, ProductService productService) {
		this.orderService = orderService;
		this.productService = productService;

	}

	@GET
	@Timed
	@Path("v1")
	@ApiOperation(value = "Get all orders")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response getOrders() {
		return Response.ok(orderService.getOrders()).build();
	}

	@GET
	@Timed
	@Path("v1/{id}")
	@ApiOperation(value = "Get order by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response getOrder(@PathParam("id") final int id) {
		return Response.ok(orderService.getOrder(id)).build();
	}

	@POST
	@Timed
	@Path("v1")
	@ApiOperation(value = "Get order by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response createOrder(@NotNull @Valid final Order order) {
		Product product = productService.getProduct(order.getProductId());
		if (null != product) {
			if (product.getStock() <= order.getQuantity()) {
				throw new WebApplicationException("Can not select these many product quantity", Status.NOT_FOUND);
			}
			Map<String, String> response = new HashMap<>();
			response.put("status", orderService.createOrder(order));
			return Response.ok(response).build();
		} else {
			throw new WebApplicationException("Invalid product id", Status.NOT_FOUND);
		}

	}

	@PUT
	@Timed
	@Path("v1/{id}")
	@ApiOperation(value = "Get order by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response editOrder(@NotNull @Valid final Order order, @PathParam("id") final int id) {
		order.setId(id);
		return Response.ok(orderService.editOrder(order)).build();
	}

	@DELETE
	@Timed
	@Path("v1/{id}")
	@ApiOperation(value = "Get order by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response deleteOrder(@PathParam("id") final int id) {
		Map<String, String> response = new HashMap<>();
		response.put("status", orderService.deleteOrder(id));
		return Response.ok(response).build();
	}
	
	@DELETE
	@Timed
	@Path("v1")
	@ApiOperation(value = "Get order by id")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error Occurred while getting data"),
			@ApiResponse(code = 404, message = "Name not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response deleteOrder() {
		Map<String, String> response = new HashMap<>();
		orderService.deleteDummyOrder();
		response.put("status", "success");
		return Response.ok(response).build();
	}

	private int getOrderedQuantityForProduct(Connection orderDbConnection, int productId) throws SQLException {
		try (PreparedStatement ps = orderDbConnection.prepareStatement(
				"select sum(quantity) as quantity from order_info where status = 'valid' and  productId = ?");) {
			ps.setInt(1, productId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					return rs.getInt("quantity");

				}
				;
				return -1;

			}
		}
	}
}
