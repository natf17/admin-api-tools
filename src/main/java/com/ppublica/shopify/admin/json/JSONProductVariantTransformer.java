package com.ppublica.shopify.admin.json;

import com.ppublica.shopify.admin.json.artcb.LMProductVariantTransformer;

/**
 * This class contains all default transformations for product variants.
 *
 */
public class JSONProductVariantTransformer {	
	
	private LMProductVariantTransformer lmTransformer = new LMProductVariantTransformer();
	
	public JSONProductVariant asPostVariantCompliant(JSONProductVariant variant) {
		// properties that're read-only: position, inventory_quantity
		// other properties excluded: id, image_id

		variant.setPosition(null);
		variant.setInventory_quantity(null);
		variant.setId(null);
		variant.setImage_id(null);
		
		lmTransformer.asPostVariantCompliant(variant);
				
		return variant;
	}

}
