package com.ppublica.shopify.admin.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * The lowest "API" for connecting to the Shopify Admin API.
 *
 * @param <T> the type of the resource
 * @param <S> the type of the resource wrapper
 * 
 */
public interface AdminApiMethods<T,S> {
	T postEntity(T entity, HttpEntityEnclosingRequestBase requestBase, CloseableHttpClient httpClient);

	T putEntity(T entity, HttpEntityEnclosingRequestBase requestBase, CloseableHttpClient httpClient);

	HttpEntity processRequestEntityAsString(S entity) throws UnsupportedEncodingException;
	
	S processResponseEntityFromString(CloseableHttpResponse rawResponse, Class<S> clazz) throws UnsupportedOperationException, IOException;


}
