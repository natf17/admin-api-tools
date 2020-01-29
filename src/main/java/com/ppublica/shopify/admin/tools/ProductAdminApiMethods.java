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
import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.wrappers.DefaultJSONProductWrapper;

/**
 * A package-visible class that contains the Shopify-specific logic for all the HttpMethods for all Admin API endpoints.
 * This class also processes any entities that will be sent as requests.
 */
class ProductAdminApiMethods implements AdminApiMethods<DefaultJSONProduct,DefaultJSONProductWrapper> {
	
	private ObjectMapper requestObjectMapper;
	private ObjectMapper responseObjectMapper;
	
	public ProductAdminApiMethods() {		
		ObjectMapper requestObjectMapper = new ObjectMapper();
		requestObjectMapper.setSerializationInclusion(Include.NON_NULL);
		this.requestObjectMapper = requestObjectMapper;
		
		ObjectMapper responseObjectMapper = new ObjectMapper();
		responseObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.responseObjectMapper = responseObjectMapper;


	}

	
	/**
	 * Processes the payload object and return a String entity to be used as the request body.
	 * 
	 * @param object the payload object
	 * @return a StringEntity
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public HttpEntity processRequestEntityAsString(DefaultJSONProductWrapper productWrapper) throws UnsupportedEncodingException {
		
		String jsonEntity = null;
		try {
			jsonEntity = requestObjectMapper.writeValueAsString(productWrapper);
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
	 * Takes the raw CloseableHttpResponse and returns the response as the desired object.
	 * 
	 * @param rawResponse The raw CloseableHttpResponse
	 * @param clazz The expected return object type
	 * @return The processed response
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	@Override
	public DefaultJSONProductWrapper processResponseEntityFromString(CloseableHttpResponse rawResponse, Class<DefaultJSONProductWrapper> clazz) throws UnsupportedOperationException, IOException {
		HttpEntity httpEntity = rawResponse.getEntity();
		InputStream inputStream = httpEntity.getContent();

		DefaultJSONProductWrapper responseObject = null;
		try {
			responseObject = responseObjectMapper.readValue(inputStream, clazz);
		}  catch(IOException ex) {
			System.out.println("DefaultJSONProduct: IOException in objectMapper.readValue() " + ex.getMessage());
			throw ex;
		} catch(Exception e) {
			System.out.println("DefaultJSONProduct: Exception thrown!");
			throw e;
		}
		
		
		return responseObject;
	}


	@Override
	public DefaultJSONProduct postEntity(DefaultJSONProduct entity, HttpEntityEnclosingRequestBase requestBase, CloseableHttpClient httpClient) {
		System.out.println("ProductAdminApiMethods POST");
		CloseableHttpResponse rawResponse = null;
		DefaultJSONProductWrapper productResponse = null;
		try {
			requestBase.setEntity(processRequestEntityAsString(new DefaultJSONProductWrapper(entity)));
			try {
				rawResponse = httpClient.execute(requestBase);
				
				try {
					System.out.println(rawResponse.getStatusLine());
					productResponse = processResponseEntityFromString(rawResponse, DefaultJSONProductWrapper.class);
				} catch(Exception ex) {
					System.out.println("DefaultJSONProduct: Error processing the response");
					
				}
			} finally {
				rawResponse.close();
			}
			
		} catch(IOException ex) {
			System.out.println("DefaultJSONProduct: IOException POSTing a product");
		}
		
		
		
		return productResponse.getProduct();
	}

	/**
	 * Nothing yet
	 */
	@Override
	public DefaultJSONProduct putEntity(DefaultJSONProduct entity, HttpEntityEnclosingRequestBase requestBase,
			CloseableHttpClient httpClient) {
		return null;
	}
	
	
}
