package com.ppublica.shopify.admin.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.JSONProductImage;
import com.ppublica.shopify.admin.json.JSONProductVariant;

public class MultiStepRequestUtility {
	
	/**
	 * Before POSTing, map image position in the product to multiple variants by their position.
	 * @param product
	 */
	public Map<Long,List<Long>> extractImageToVariantMapping(DefaultJSONProduct product) {
		Map<Long,List<Long>> imagePositionToVariantPositions = new HashMap<>();
		
		JSONProductVariant[] variants = product.getVariants();
		JSONProductImage[] images = product.getImages();
		
		if(variants == null || images == null) {
			return imagePositionToVariantPositions;
		}
		
		// create a Map of variant id to variant position
		Map<Long,Long> variantIdsToPosition = new HashMap<>();
		for(JSONProductVariant variant : variants) {
			variantIdsToPosition.put(variant.getId(), variant.getPosition());
		}
		
		// create a Map of image position to a List of variant positions
		List<Long> variantPositions = null;
		for(JSONProductImage image : images) {
			variantPositions = new ArrayList<>();
			Long[] variantIds = image.getVariant_ids();
			for(Long variantId : variantIds) {
				variantPositions.add(variantIdsToPosition.get(variantId));
			}
			imagePositionToVariantPositions.put(image.getPosition(), variantPositions);
		}
		
		return imagePositionToVariantPositions;
		
		
	}
	
	/**
	 * After POSTing, map variant position to variant id, and image position to image id to save the image to each
	 * variant now that we have an id for each.
	 */
	public Map<Long,List<Long>> mapVariantAndImageIdsAndPositions(DefaultJSONProduct product, Map<Long,List<Long>> imagePositionToVariantPositions) {
		Map<Long,List<Long>> imageIdToVariantIds = new HashMap<>();

		JSONProductVariant[] variants = product.getVariants();
		JSONProductImage[] images = product.getImages();
		
		if(variants == null || images == null) {
			return imageIdToVariantIds;
		}	
		
		// extract all the variants from the newly created product
		// create a map of variant position to id
		Map<Long,Long> variantPositionsToId = new HashMap<>();
		for(JSONProductVariant variant : variants) {
			variantPositionsToId.put(variant.getPosition(), variant.getId());
		}
		
		// extract all the images from the newly created product
		// create a map of image position to id	
		Map<Long,Long> imagePositionToImageId = new HashMap<>();
		for(JSONProductImage image : images) {
			imagePositionToImageId.put(image.getPosition(), image.getId());
		}
				

		// create a Map of variant ids to multiple image ids 
		Long imageId;
		Long imagePosition;
		List<Long> originalVariantPositions = null;
		List<Long> newVariantIds = null;
		for(Map.Entry<Long,Long> imagePositionToId : imagePositionToImageId.entrySet()) {
			// obtain the image id for this image position
			imagePosition = imagePositionToId.getKey();
			imageId = imagePositionToId.getValue();
						
			// for every image position in the Map, get the corresponding List of variant positions, which were hopefully
			// saved in the same order and thus have the same positions
			originalVariantPositions = imagePositionToVariantPositions.get(imagePosition);
			
			// obtain a list of variant ids for this image position using the original variant positions
			newVariantIds = new ArrayList<>();
			for(Long originalPosition : originalVariantPositions) {
				newVariantIds.add(variantPositionsToId.get(originalPosition));
			}
			
			imageIdToVariantIds.put(imageId, newVariantIds);

			
		}
		
		// return the new map
		return imageIdToVariantIds;
				
	}

}
