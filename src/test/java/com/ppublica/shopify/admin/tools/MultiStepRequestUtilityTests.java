package com.ppublica.shopify.admin.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.JSONProductImage;
import com.ppublica.shopify.admin.json.JSONProductVariant;

public class MultiStepRequestUtilityTests {

	@Test
	public void mapVariantAndImageIdsAndPositionsSuccess() {
		MultiStepRequestUtility utility = new MultiStepRequestUtility();
		
		JSONProductVariant variant1 = new JSONProductVariant();
		JSONProductVariant variant2 = new JSONProductVariant();
		JSONProductVariant variant3 = new JSONProductVariant();
		
		variant1.setPosition(1L);
		variant2.setPosition(2L);
		variant3.setPosition(3L);
		
		variant1.setId(34L);
		variant3.setId(44L);
		variant2.setId(54L);
		
		
		JSONProductImage image1 = new JSONProductImage();
		JSONProductImage image2 = new JSONProductImage();
		JSONProductImage image3 = new JSONProductImage();
		
		image1.setPosition(1L);
		image2.setPosition(2L);
		image3.setPosition(3L);
		
		image1.setId(38L);
		image2.setId(48L);
		image3.setId(58L);

		
		
		DefaultJSONProduct product = new DefaultJSONProduct();
		product.setVariants(new JSONProductVariant[] {variant1, variant2, variant3});
		product.setImages(new JSONProductImage[] {image1, image2, image3});
		
		Map<Long,List<Long>> imagePositionToVariantPositions = new HashMap<>();
		imagePositionToVariantPositions.put(1L, Arrays.asList(1L,2L));
		imagePositionToVariantPositions.put(2L, Arrays.asList(1L,3L));
		imagePositionToVariantPositions.put(3L, Arrays.asList(2L,3L));
		
		
		Map<Long,List<Long>> imageIdToVariantIds = utility.mapVariantAndImageIdsAndPositions(product, imagePositionToVariantPositions);
		List<Long> variantIdsForImage1 =  imageIdToVariantIds.get(38L);
		List<Long> variantIdsForImage2 =  imageIdToVariantIds.get(48L);
		List<Long> variantIdsForImage3 =  imageIdToVariantIds.get(58L);
		
		Assert.assertTrue(variantIdsForImage1.size() == 2);
		Assert.assertTrue(variantIdsForImage2.size() == 2);
		Assert.assertTrue(variantIdsForImage3.size() == 2);
	}

}
