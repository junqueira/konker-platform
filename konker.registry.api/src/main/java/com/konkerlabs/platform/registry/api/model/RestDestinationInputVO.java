package com.konkerlabs.platform.registry.api.model;

import java.util.Map;

import com.konkerlabs.platform.registry.business.model.RestDestination.RestDestinationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(
		value = "Rest Destination input",
		discriminator = "com.konkerlabs.platform.registry.api.model")
public class RestDestinationInputVO {
	
	@ApiModelProperty(
			value = "Descriptive name to identify the rest destination",
			example = "konker API services", 
			required = true, 
			position = 1)
	protected String name;
	
	@ApiModelProperty(
			value = "Kind of HTTP method to this rest destination", 
			example = "GET", 
			position = 2)
	protected String method;
	
	@ApiModelProperty(
			value = "HTTP request header to this rest destination. (e.g. {\"Content-Type\": \"application/json\"})", 
			position = 3)
	protected Map<String, String> headers;
	
	@ApiModelProperty(
			value = "Address of the rest destination", 
			example = "http://www.konker.com/api", 
			required = true, 
			position = 4)
	protected String serviceURI;
	
	@ApiModelProperty(
			value = "Username to authentication on the address of rest destination", 
			example = "myaccount@konkerlabs.com", 
			position = 5)
	protected String serviceUsername;

	@ApiModelProperty(
			value = "Password to authentication on the address of rest destination", 
			example = "mypassword123", 
			position = 6)
	protected String servicePassword;
	
	@ApiModelProperty(
			value = "Type of rest destination", 
			example = "CUSTOM_BODY",
			required = true, 
			position = 7)
	protected RestDestinationType type;
	
	@ApiModelProperty(
			value = "Body message in JSON format", 
			example = "{'temperature' : '28°C'}",
			position = 8)
	protected String body;
	
	@ApiModelProperty(
			value = "Property to indicate whether the resting destination is active or not", 
			example = "true", 
			position = 9)
	protected boolean active;

}
