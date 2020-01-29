package com.ppublica.shopify.admin.json;

/**
 * This class contains all default transformations for product options.
 *
 */
public class JSONProductOptionsTransformer {

	public JSONProductOption asPostVariantCompliant(JSONProductOption options) {
		// properties excluded: id, product_id

		options.setId(null);
		options.setProduct_id(null);
		
		return options;
	}
}
