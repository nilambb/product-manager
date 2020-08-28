package com.lti.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AppConfiguration extends Configuration {
	@JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
	
	@Valid
    @NotNull
    @JsonProperty("productDb")
    private DataSourceFactory productDb = new DataSourceFactory();
	
	@Valid
    @NotNull
    @JsonProperty("orderDb")
    private DataSourceFactory orderDb = new DataSourceFactory();
	
	 /**
     * Gets the swaggerBundleConfiguration.
     *
     * @return the value swaggerBundleConfiguration.
     */
    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    /**
     * Sets the swaggerBundleConfiguration.
     *
     * @param swaggerBundleConfiguration value.
     */
    public void setSwaggerBundleConfiguration(final SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }
    
    public DataSourceFactory getProductDb() {
        return productDb;
    }
    
    public DataSourceFactory getOrderDb() {
        return orderDb;
    }
}
