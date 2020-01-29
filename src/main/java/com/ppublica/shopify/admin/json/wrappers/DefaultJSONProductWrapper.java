package com.ppublica.shopify.admin.json.wrappers;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;

public class DefaultJSONProductWrapper {

	private DefaultJSONProduct product;
	
	public DefaultJSONProductWrapper() { }
	public DefaultJSONProductWrapper(DefaultJSONProduct product) {
		this.product = product;
	}
	
	public void setProduct(DefaultJSONProduct product) {
		this.product = product;
	}

	
	public DefaultJSONProduct getProduct() {
		return this.product;
	}
}
