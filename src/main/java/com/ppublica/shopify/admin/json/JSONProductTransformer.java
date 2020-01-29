package com.ppublica.shopify.admin.json;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ppublica.shopify.admin.json.artcb.JSONProductArtCB;
import com.ppublica.shopify.admin.json.artcb.LMProductTransformer;

/**
 * This class contains all default transformations for products. It uses transformers for images, variants, and options to
 * fully transform the product.
 * 
 */
public class JSONProductTransformer {
	
	private JSONImageTransformer imageTransformer = new JSONImageTransformer();
	private JSONProductVariantTransformer productVariantTransformer = new JSONProductVariantTransformer();
	private JSONProductOptionsTransformer productOptionsTransformer = new JSONProductOptionsTransformer();
	private LMProductTransformer lmTransformer = new LMProductTransformer();
	
	public List<DefaultJSONProduct> asDefaultProducts(List<JSONProductArtCB> customProducts) {
		
		List<DefaultJSONProduct> defaultProducts = customProducts.stream()
														.map(i -> this.asDefaultProduct(i))
														.collect(Collectors.toList());

		return defaultProducts;
	}
	
	/**
	 * Transforms a JSONProductArtCB into the Shopify-compliant DefaultJSONProduct. Called when reading
	 * from a file.
	 * 
	 * @param customProduct the custom product (with a tags array)
	 * @return the default DefaultJSONProduct
	 */
	public DefaultJSONProduct asDefaultProduct(JSONProductArtCB customProduct) {
		// this custom type only differs from the default by its tag representation
		String[] tags = customProduct.getTags();
		
		String tagsString = Stream.of(tags)
				.reduce((i,j) -> {
					StringBuilder str = new StringBuilder(i);
					str.append(",");
					str.append(j);
					return str.toString();
				}).get();
		
		DefaultJSONProduct newDefaultProd = new DefaultJSONProduct(customProduct);
		newDefaultProd.setTags(tagsString);
		
		return newDefaultProd;
	}

	/**
	 * Makes sure the DefaultJSONProduct doesn't specify fields Shopify might send upon a GET, but will not accept
	 * in a POST. It then delegates to JSONImageTransformer, JSONProductVariantTransformer, and JSONProductOptionsTransformer,
	 * and to LMProductTransformer.
	 * 
	 * @param productToPost DefaultJSONProduct we want to POST
	 * @return the DefaultJSONProduct we will POST
	 */
	public DefaultJSONProduct asPostProductCompliant(DefaultJSONProduct productToPost) {
		// READ-only fields: created_at, id, updated_at
		// also excluding: published_at, images unless it isn't assigned to a variant
		
		productToPost.setCreated_at(null);
		productToPost.setId(null);
		productToPost.setUpdated_at(null);
		productToPost.setPublished_at(null);
		
		JSONProductImage[] images = productToPost.getImages();
		if(images != null) {
			for(JSONProductImage image : images) {
				imageTransformer.asPostImageCompliant(image);

			}
		}
		
			
		JSONProductVariant[] variants = productToPost.getVariants();
		if(variants != null) {
			for(JSONProductVariant variant : variants) {
				productVariantTransformer.asPostVariantCompliant(variant);

			}
		}

		JSONProductOption[] options = productToPost.getOptions();
		if(options != null) {
			for(JSONProductOption option : options) {
				productOptionsTransformer.asPostVariantCompliant(option);

			}
		}
		
		lmTransformer.transform(productToPost);
		
		
		return productToPost;
		
		
	}
	
	/**
	 * Used to merge a product's old images with new ones after a product's images have been updated (eg by assigning them 
	 * to variants).
	 * 
	 * @param product
	 * @param newImages
	 * @return
	 */
	public DefaultJSONProduct mergeNewProductImagesWithOld(DefaultJSONProduct product, List<JSONProductImage> newImages) {
		JSONProductImage[] oldImages = product.getImages();
		
		List<Long> oldImagesIdsOrdered = Stream.of(oldImages)
													.map(i -> i.getId())
													.collect(Collectors.toList());
		
		int imagePositionInOriginalArray = 0;
		for(JSONProductImage newImage : newImages) {
			imagePositionInOriginalArray = oldImagesIdsOrdered.indexOf(newImage.getId());
			if(imagePositionInOriginalArray > -1) {
				oldImages[imagePositionInOriginalArray] = newImage;
			} else {
				throw new RuntimeException("An image with id " + newImage.getId()
												+ "does't exist in the product with id " + product.getId());
			}
		}
		
		// redundant...
		product.setImages(oldImages);
		
		return product;
	}
	
	public void setLMProductTransformer(LMProductTransformer lMProductTransformer) {
		this.lmTransformer = lMProductTransformer;
	}
		
		

}
