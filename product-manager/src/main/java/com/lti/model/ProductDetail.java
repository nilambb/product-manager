package com.lti.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDetail {
	private int id;
	private String name;
	private String companyName;
	private String categoryName;
	private String colour;
	private String description;
	private float price;
	private float discount;
	private int stock;
	private float discountedPrice;
	private int availableStock;
}
