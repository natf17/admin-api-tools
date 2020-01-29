package com.ppublica.shopify.admin.json;

import java.util.List;

/**
 * This class contains all default transformations for images.
 */
public class JSONImageTransformer {
	
	/**
	 * Makes sure the JSONProductImage doesn't specify fields Shopify might send upon a GET, but will not accept
	 * in a POST. This method only supports POSTing an image by having Shopify download it from the src url.
	 * 
	 * @param image The image with potentially read-only properties
	 * @return The JSONProductImage that can be POSTed
	 */
	public JSONProductImage asPostImageCompliant(JSONProductImage image) {
		// All properties except "src" and "position" are set to null.
		image.setCreated_at(null);
		image.setHeight(null);
		image.setId(null);
		image.setProduct_id(null);
		image.setUpdated_at(null);
		image.setVariant_ids(null);
		image.setWidth(null);
		
		return image;
	}

	/**
	 * Transform an image id and multiple variant ids into a JSONProductImage.
	 * 
	 */
	public JSONProductImage productImageFromIdAndVariantIds(Long id, List<Long> variantIds) {
		Long[] variantIdsArr  = variantIds.toArray(new Long[0]);
		
		JSONProductImage productImage = new JSONProductImage();
		productImage.setId(id);
		productImage.setVariant_ids(variantIdsArr);
		
		return productImage;
		
	}
}
