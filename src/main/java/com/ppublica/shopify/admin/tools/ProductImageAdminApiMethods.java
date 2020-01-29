package com.ppublica.shopify.admin.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppublica.shopify.admin.json.JSONProductImage;
import com.ppublica.shopify.admin.json.wrappers.JSONProductImageWrapper;

class ProductImageAdminApiMethods implements AdminApiMethods<JSONProductImage,JSONProductImageWrapper> {
	
	private ObjectMapper requestObjectMapper;
	private ObjectMapper responseObjectMapper;
	
	public ProductImageAdminApiMethods() {		
		ObjectMapper requestObjectMapper = new ObjectMapper();
		requestObjectMapper.setSerializationInclusion(Include.NON_NULL);
		this.requestObjectMapper = requestObjectMapper;
		
		ObjectMapper responseObjectMapper = new ObjectMapper();
		responseObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.responseObjectMapper = responseObjectMapper;


	}
	
	/**
	 * Nothing yet
	 */
	@Override
	public JSONProductImage postEntity(JSONProductImage entity, HttpEntityEnclosingRequestBase requestBase,
			CloseableHttpClient httpClient) {
		return null;
	}
	
	/**
	 * PUTs the image via JSON, and returns the response body as a JSONProductImage.
	 * 
	 */
	@Override
	public JSONProductImage putEntity(JSONProductImage entity, HttpEntityEnclosingRequestBase requestBase,
			CloseableHttpClient httpClient) {
		System.out.println("ProductImageAdminApiMethods PUT");
		
		CloseableHttpResponse rawResponse = null;
		JSONProductImageWrapper imageResponse = null;
		try {
			requestBase.setEntity(processRequestEntityAsString(new JSONProductImageWrapper(entity)));
			try {
				rawResponse = httpClient.execute(requestBase);
				System.out.println(rawResponse.getStatusLine());

				try {
					imageResponse = processResponseEntityFromString(rawResponse, JSONProductImageWrapper.class);
				} catch(Exception ex) {
					System.out.println("Error processing the response");
					
				}
			} finally {
				rawResponse.close();
			}
			
		} catch(IOException ex) {
			System.out.println("IOException POSTing a product");
		}
	
		
		return imageResponse.getImage();
		
		
		
	}

	/**
	 * Same as ProductAdminApiMethods
	 */
	@Override
	public HttpEntity processRequestEntityAsString(JSONProductImageWrapper entity) throws UnsupportedEncodingException {
		String jsonEntity = null;
		try {
			jsonEntity = requestObjectMapper.writeValueAsString(entity);
		} catch(JsonProcessingException ex) {
			System.out.println("Error writing JSON entity as String");
		}
		
		//System.out.println("POST JSON: " + jsonEntity);
		
		if(jsonEntity == null || jsonEntity.isEmpty()) {
			return null;
		}
		
		return new StringEntity(jsonEntity);
	}

	/**
	 * Same as ProductAdminApiMethods
	 */
	@Override
	public JSONProductImageWrapper processResponseEntityFromString(CloseableHttpResponse rawResponse,
			Class<JSONProductImageWrapper> clazz) throws UnsupportedOperationException, IOException {
		
		HttpEntity httpEntity = rawResponse.getEntity();
		InputStream inputStream = httpEntity.getContent();

		JSONProductImageWrapper responseObject = null;
		try {
			responseObject = responseObjectMapper.readValue(inputStream, clazz);
		}  catch(IOException ex) {
			System.out.println("IOException in objectMapper.readValue() " + ex.getMessage());
			throw ex;
		} catch(Exception e) {
			System.out.println("Exception thrown!");
			throw e;
		}
		
		
		return responseObject;
	}


}
