package com.ppublica.shopify.admin.json;

public class JSONProductImage {
	private String created_at;
	private Long id;
	private Long position;
	private Long product_id;
	private Long[] variant_ids;
	private String src;
	private Long width;
	private Long height;
	private String updated_at;
	
	
	public String getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPosition() {
		return position;
	}
	
	public void setPosition(Long position) {
		this.position = position;
	}
	
	public Long getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	
	public Long[] getVariant_ids() {
		return variant_ids;
	}
	
	public void setVariant_ids(Long[] variant_ids) {
		this.variant_ids = variant_ids;
	}
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	public Long getWidth() {
		return width;
	}
	
	public void setWidth(Long width) {
		this.width = width;
	}
	
	public Long getHeight() {
		return height;
	}
	
	public void setHeight(Long height) {
		this.height = height;
	}
	
	public String getUpdated_at() {
		return updated_at;
	}
	
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	

}
