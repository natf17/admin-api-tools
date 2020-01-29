package com.ppublica.shopify.admin.json;

public class DefaultJSONProduct extends JSONProduct {

	public DefaultJSONProduct() { }
	
	public DefaultJSONProduct(JSONProduct prod) {
		this.setBody_html(prod.getBody_html());
		this.setCreated_at(prod.getCreated_at());
		this.setHandle(prod.getHandle());
		this.setId(prod.getId());
		this.setImages(prod.getImages());
		this.setOptions(prod.getOptions());
		this.setProduct_type(prod.getProduct_type());
		this.setPublished_at(prod.getPublished_at());
		this.setPublished_scope(prod.getPublished_scope());
		this.setTemplate_suffix(prod.getTemplate_suffix());
		this.setTitle(prod.getTitle());
		this.setUpdated_at(prod.getUpdated_at());
		this.setVariants(prod.getVariants());
		this.setVendor(prod.getVendor());
		this.setPublished(prod.isPublished());
	}
	private String tags;
	
	public String getTags() {
		return this.tags;
	}
	
	public void setTags(String tags) {
		this.tags = tags;
	}
}
