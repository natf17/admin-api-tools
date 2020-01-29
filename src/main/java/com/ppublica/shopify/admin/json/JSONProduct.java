package com.ppublica.shopify.admin.json;

public class JSONProduct {
	// properties excluded: metafields_global_title_tag, metafields_global_description_tag

	private String body_html;
	private String created_at;
	private String handle;
	private Long id;
	private JSONProductImage[] images;
	private JSONProductOption[] options;
	private String product_type;
	private String published_at;
	private String published_scope;
	// private String tags; to be added by implementing classes
	private String template_suffix;
	private String title;
	private String updated_at;
	private JSONProductVariant[] variants;
	private String vendor;
	private boolean published;


	

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody_html() {
		return this.body_html;
	}
	
	public void setBody_html(String body_html) {
		this.body_html = body_html;
	}
	
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getProduct_type() {
		return this.product_type;
	}
	
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONProductImage[] getImages() {
		return images;
	}

	public void setImages(JSONProductImage[] images) {
		this.images = images;
	}

	public String getPublished_at() {
		return published_at;
	}

	public void setPublished_at(String published_at) {
		this.published_at = published_at;
	}

	public String getPublished_scope() {
		return published_scope;
	}

	public void setPublished_scope(String published_scope) {
		this.published_scope = published_scope;
	}

	public String getTemplate_suffix() {
		return template_suffix;
	}

	public void setTemplate_suffix(String template_suffix) {
		this.template_suffix = template_suffix;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public JSONProductVariant[] getVariants() {
		return variants;
	}

	public void setVariants(JSONProductVariant[] variants) {
		this.variants = variants;
	}

	public JSONProductOption[] getOptions() {
		return options;
	}

	public void setOptions(JSONProductOption[] options) {
		this.options = options;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
}
