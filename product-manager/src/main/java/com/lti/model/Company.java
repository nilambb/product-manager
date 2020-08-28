package com.lti.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
	@JsonProperty
	private int id;
	@JsonProperty
	private String companyName;
	
	@Override
	public String toString() {
		return "Company{" + "id=" + id + ", companyName= '" + companyName + "' }";
	}
}
