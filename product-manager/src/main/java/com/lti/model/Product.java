package com.lti.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
	private int id;
	private String name;
	private int companyId;
	private int categoryId;
	private String colour;
	private String description;
	private float price;
	private float discount;
	private int stock;
}
