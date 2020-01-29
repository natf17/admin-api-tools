package com.ppublica.shopify.admin.json;

public class JSONInventoryLevel {
	private Long location_id;
	private Long inventory_item_id;
	private Long available;
	
	
	public Long getLocation_id() {
		return location_id;
	}
	
	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}
	
	public Long getInventory_item_id() {
		return inventory_item_id;
	}
	
	public void setInventory_item_id(Long inventory_item_id) {
		this.inventory_item_id = inventory_item_id;
	}
	
	public Long getAvailable() {
		return available;
	}
	
	public void setAvailable(Long available) {
		this.available = available;
	}

	

}
