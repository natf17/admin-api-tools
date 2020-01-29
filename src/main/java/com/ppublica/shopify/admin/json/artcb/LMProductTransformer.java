package com.ppublica.shopify.admin.json.artcb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.JSONProductVariant;

/**
 * Transformations/modifications to a product when posting to our store.
 */
public class LMProductTransformer implements CustomResourceTransformer {
	
	private String product_type = "Bridal";
	private String product_vendor = "Louis Martin Jewelers - Rockefeller Center - NYC";
	private final boolean isPublished = true;
	private List<String> customTags = new ArrayList<>();
	private Map<String,String> filterTags = new HashMap<>();
	
	{
		filterTags.put("metal_platinum", "$f$metals$Platinum");
		filterTags.put("metal_gold", "$f$metals$Gold");
		filterTags.put("metal_y_gold", "$f$metals$Yellow gold");
		filterTags.put("metal_r_gold", "$f$metals$Rose gold");
		filterTags.put("metal_w_gold", "$f$metals$White gold");
		filterTags.put("stone_diamond", "$f$stones$Diamond");
		filterTags.put("category", "$f$bridalcat$Engagement Rings");
	}
	
	private Map<String,String> otherTags = new HashMap<>();
	
	{
		otherTags.put("metal_gold", "gold");
		otherTags.put("metal_y_gold", "yellow gold");
		otherTags.put("metal_r_gold", "rose gold");
		otherTags.put("metal_w_gold", "white gold");
		otherTags.put("metal_platinum", "platinum");
		otherTags.put("18kt", "18kt");
		otherTags.put("14kt", "14kt");
		otherTags.put("stone_diamond", "diamond");
		otherTags.put("category", "engagement-rings");

	}
	
	/**
	 * All products
	 * 
	 *  - type Bridal
	 *  - vendor: ArtCarved Bridal
	 * 
	 * They all have the tag: art_carved_bridal
	 * 
	 * 
	 */
	@Override
	public DefaultJSONProduct transform(DefaultJSONProduct productToPost) {
		productToPost.setProduct_type(product_type);
		productToPost.setVendor(product_vendor);
		//productToPost.setPublished_at(null);
		productToPost.setPublished(isPublished);
		
		productToPost.setTags(generateTags(productToPost));
		return productToPost;
	}
	
	private String generateTags(DefaultJSONProduct productToPost) {
		Set<String> filterTags = new HashSet<>();
		Set<String> otherTags = new HashSet<>();
		
		boolean hasWhiteGold = false;
		boolean hasYellowGold = false;
		boolean hasRoseGold = false;
		boolean hasPlatinum = false;
		boolean has18kt = false;
		boolean has14kt = false;
		boolean hasDiamond = false;
		
		
		if(productToPost.getTitle().contains("diamond") || productToPost.getTitle().contains("Diamond")) {
			hasDiamond = true;
			
		}
		

		
		JSONProductVariant[] variants = productToPost.getVariants();
		String variantTitle = null;
		for(JSONProductVariant variant : variants) {
			variantTitle = variant.getTitle();
			if(variantTitle.contains("14K Yellow")) {
				hasYellowGold = true;
				has14kt = true;
				
			} else if(variantTitle.contains("18K Yellow")) {
				hasYellowGold = true;
				has18kt = true;
				
			} else if(variantTitle.contains("14K White")) {
				hasWhiteGold = true;
				has14kt = true;
				
			} else if(variantTitle.contains("18K White")) {
				hasWhiteGold = true;
				has18kt = true;
				
			} else if(variantTitle.contains("14K Rose")) {
				hasRoseGold = true;
				has14kt = true;
				
			} else if(variantTitle.contains("18K Rose")) {
				hasRoseGold = true;
				has18kt = true;
				
			} else if(variantTitle.contains("Platinum") || variantTitle.contains("platinum")) {
				hasPlatinum = true;
				
			}
				
		}


		if(hasWhiteGold) {
			filterTags.add("metal_gold");
			otherTags.add("metal_gold");
			
			filterTags.add("metal_w_gold");
			otherTags.add("metal_w_gold");
		}
		
		if(hasYellowGold) {
			filterTags.add("metal_gold");
			otherTags.add("metal_gold");
			
			filterTags.add("metal_y_gold");
			otherTags.add("metal_y_gold");
		}
		
		if(hasRoseGold) {
			filterTags.add("metal_gold");
			otherTags.add("metal_gold");
			
			filterTags.add("metal_r_gold");
			otherTags.add("metal_r_gold");
		}
		
		if(hasPlatinum) {
			filterTags.add("metal_platinum");
			otherTags.add("metal_platinum");
			
		}
		
		if(has14kt) {
			otherTags.add("14kt");
			
		}
		
		if(has18kt) {
			otherTags.add("18kt");
			
		}
		
		if(hasDiamond) {
			filterTags.add("stone_diamond");
			otherTags.add("stone_diamond");
			
		}
		
		filterTags.add("category");
		otherTags.add("category");
		
		StringBuilder builder = new StringBuilder();
		
		for(String customTag : customTags) {
			builder.append(customTag + ",");
		}
				
		for(String filterTag : filterTags) {
			builder.append(this.filterTags.get(filterTag));
			builder.append(",");
		}
		
		for(String otherTag : otherTags) {
			builder.append(this.otherTags.get(otherTag));
			builder.append(",");
		}
		
		// remove last tag
		String tags = "";
		if(builder.lastIndexOf(",") == builder.length() - 1) {
			System.out.println("Cleaning tag comma");
			tags = builder.substring(0, builder.length() - 1);
			
		} else {
			tags = builder.toString();
		}
		
		return tags;
	}
	
	public void setLMCategory(String categoryTag, String categoryFilterTag) {
		filterTags.put("category", categoryFilterTag);
		otherTags.put("category", categoryTag);

	}
	
	public void setLMProductType(String productType) {
		product_type = productType;

	}
	
	public void setLMProductVendor(String vendor) {
		product_vendor = vendor;

	}
	
	public void addCustomTag(String customTag) {
		customTags.add(customTag);
	}

}
