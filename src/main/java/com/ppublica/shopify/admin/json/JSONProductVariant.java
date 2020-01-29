package com.ppublica.shopify.admin.json;

public class JSONProductVariant {
	// properties excluded: presentment_prices
	private String barcode;
	private String compare_at_price;
	private String fulfillment_service;
	private Long grams;
	private Long id;
	private Long image_id;
	private Long inventory_item_id;
	private String inventory_management;
	private String inventory_policy;
	private Long inventory_quantity;
	private String option1;
	private String option2;
	private String option3;
	private Long position;
	private String price;
	private String product_id;
	private String sku;
	private boolean taxable;
	private String title;
	private String updated_at;
	private Long weight;
	private String weight_unit;

	
	public String getPrice() {
		return this.price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getCompare_at_price() {
		return this.compare_at_price;
	}
	
	public void setCompare_at_price(String compare_at_price) {
		this.compare_at_price = compare_at_price;
	}

	public String getSku() {
		return this.sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	public Long getInventory_quantity() {
		return this.inventory_quantity;
	}
	
	public void setInventory_quantity(Long inventory_quantity) {
		this.inventory_quantity = inventory_quantity;
	}
	
	public String getInventory_management() {
		return this.inventory_management;
	}
	
	public void setInventory_management(String inventory_management) {
		this.inventory_management = inventory_management;
	}
	
	public Long getInventory_item_id() {
		return inventory_item_id;
	}

	public void setInventory_item_id(Long inventory_item_id) {
		this.inventory_item_id = inventory_item_id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getFulfillment_service() {
		return fulfillment_service;
	}

	public void setFulfillment_service(String fulfillment_service) {
		this.fulfillment_service = fulfillment_service;
	}

	public Long getGrams() {
		return grams;
	}

	public void setGrams(Long grams) {
		this.grams = grams;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}

	public String getInventory_policy() {
		return inventory_policy;
	}

	public void setInventory_policy(String inventory_policy) {
		this.inventory_policy = inventory_policy;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public boolean isTaxable() {
		return taxable;
	}

	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getWeight_unit() {
		return weight_unit;
	}

	public void setWeight_unit(String weight_unit) {
		this.weight_unit = weight_unit;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}
	
}
