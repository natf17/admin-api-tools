package com.ppublica.shopify.admin.json.artcb;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;

/**
 * Store-specific transformations when posting a resource.
 */
public interface CustomResourceTransformer {
	DefaultJSONProduct transform(DefaultJSONProduct productToPost);

}
