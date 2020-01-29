package com.ppublica.shopify.admin.json.artcb;

import com.ppublica.shopify.admin.json.JSONProduct;

/**
 * This class is a representation of Shopify products as read from a file from a particular store.
 *
 */
public class JSONProductArtCB extends JSONProduct {
	private String[] tags;
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public String[] getTags() {
		return this.tags;
	}
	
}
