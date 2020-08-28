package com.lti.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order {
	@JsonProperty
	private int id;
	
	@JsonProperty
	private int productId;
	
	@JsonProperty
	private int quantity;
	
	@JsonProperty
	private String status;
	
	@JsonProperty
	private int customerId;
	
}
