package com.ppublica.shopify.admin.json;

public class JSONProductOption {
	private Long id;
	private Long product_id;
	private String name;
	private Long position;
	private String[] values;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	
	
}
