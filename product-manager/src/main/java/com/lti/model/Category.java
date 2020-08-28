package com.lti.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Category {
	@JsonProperty
	private int id;
	@JsonProperty
	private String categoryName;
}
