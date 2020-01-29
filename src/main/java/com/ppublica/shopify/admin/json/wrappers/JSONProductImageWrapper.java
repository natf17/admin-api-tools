package com.ppublica.shopify.admin.json.wrappers;

import com.ppublica.shopify.admin.json.JSONProductImage;

public class JSONProductImageWrapper {
	private JSONProductImage image;

	public JSONProductImageWrapper() { }
	
	public JSONProductImageWrapper(JSONProductImage image) {
		this.image = image;
	}
	
	public JSONProductImage getImage() {
		return image;
	}

	public void setImage(JSONProductImage image) {
		this.image = image;
	}


}
