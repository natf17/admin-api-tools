package com.ppublica.shopify.admin.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.JSONImageTransformer;
import com.ppublica.shopify.admin.json.JSONProductImage;
import com.ppublica.shopify.admin.json.JSONProductTransformer;

/*
 * A facade to interact with the Shopify Admin API. It configures the HttpClient for the request, but delegates to 
 * ShopifyAdminInfo for information about the destination (paths, headers,...), to JSONProductTransformer to make sure 
 * the product conforms to Shopify specifications, and to AdminApiMethods to actually make the request. The MultiStepRequestUtility 
 * takes care of extracting the necessary information for making multiple requests to complete creating the product. Whereas
 * AdmiApiMethods can only make one request per method call, this class takes care of multiple requests required for adding
 * a new product to the store.
 * 
 */
public class AdminApiTools {
	private ShopifyAdminInfo adminInfo;
	private JSONProductTransformer productTransformer = new JSONProductTransformer();
	private JSONImageTransformer imageTransformer = new JSONImageTransformer();
	private ProductAdminApiMethods productAdminApiMethods = new ProductAdminApiMethods();
	private ProductImageAdminApiMethods imageAdminApiMethods = new ProductImageAdminApiMethods();
	private MultiStepRequestUtility multiStepRequestUtility = new MultiStepRequestUtility();
	
	public AdminApiTools(ShopifyAdminInfo info) {
		this.adminInfo = info;

	}

	/**
	 * Post the given list of products, delegating to postProduct(DefaultJSONProduct).
	 * 
	 * @param prods The products to save to the Shopify store.
	 * @return The list of products as returned by Shopify
	 */
	public List<DefaultJSONProduct> postProducts(List<DefaultJSONProduct> prods) {
		List<DefaultJSONProduct> responseProducts = new ArrayList<>();
				
		for(DefaultJSONProduct prod : prods) {
			responseProducts.add(this.postProduct(prod));
		}
		
		return responseProducts;
	}
	
	/**
	 * 
	 * Use ProductAdminApiMethods to POST the product. It is aware that adding the product will most likely require multiple 
	 * steps. MultiStepRequestUtility is used to this end  - to extract all the information necessary for the
	 * subsequent steps. It then calls JSONProductTransformer to make sure the product has the correct 
	 * properties set and then invokes the AdminInfo to create the base request. Before delegating to ProductAdminApiMethods 
	 * to save the product, it asks AdminInfo to check the "bucket". Subsequent steps are then executed.
	 * 
	 * @param prod The product to be saved 
	 * @return The DefaultJSONProduct response object
	 */
	public DefaultJSONProduct postProduct(DefaultJSONProduct prod) {
		System.out.println("AdminApiTools POST product----------");
		
		System.out.println("extracting image to variant mapping");
		Map<Long,List<Long>> imageVariantMappingOriginal = multiStepRequestUtility.extractImageToVariantMapping(prod);
		
		System.out.println("trasforming product");
		prod = productTransformer.asPostProductCompliant(prod);
		
		HttpEntityEnclosingRequestBase request = adminInfo.getPostProductRequestBase();
		CloseableHttpClient httpClient = getPostHttpClient();
		
		System.out.println("AdminApiTools checking bucket");
		adminInfo.checkBucket();
		
		System.out.println("AdminApiTools calling ProductAdminApiMethods");
		DefaultJSONProduct productResponse = productAdminApiMethods.postEntity(prod, request, httpClient);
		
		Long productId = productResponse.getId();
		System.out.println("AdminApiTools created product " + productId);
		
		// assign newly created images to variants
		Map<Long,List<Long>>imageVariantMappingNew = multiStepRequestUtility.mapVariantAndImageIdsAndPositions(productResponse, imageVariantMappingOriginal);
		
		List<JSONProductImage> newImages = new ArrayList<>();
		for(Map.Entry<Long,List<Long>> imageToVariant : imageVariantMappingNew.entrySet()) {
			newImages.add(this.addExistingProductImageToProductVariants(productId, imageToVariant.getKey(), imageToVariant.getValue()));
		}

		// now that the images have been assiged to variants, update their references in the product object
		productResponse = productTransformer.mergeNewProductImagesWithOld(productResponse, newImages);

		
		System.out.println("--------------------------------------------");
		System.out.println("--------------------------------------------");
		
		return productResponse;
	}
	
	/**
	 * Use ImageAdminApiMethods to assign an existing image to multiple product variants. It is "bucket-aware".
	 *  
	 * @param productId
	 * @param imageId
	 * @param variantIds
	 * @return
	 */
	public JSONProductImage addExistingProductImageToProductVariants(Long productId, Long imageId, List<Long> variantIds) {
		System.out.println("AdminApiTools PUT image to variants ------------");
		JSONProductImage payload = imageTransformer.productImageFromIdAndVariantIds(imageId, variantIds);
		
		HttpEntityEnclosingRequestBase request = adminInfo.getPutImageRequestBase(productId, imageId);
		CloseableHttpClient httpClient = getPostHttpClient();

		System.out.println("AdminApiTools checking bucket");
		adminInfo.checkBucket();
		
		System.out.println("AdminApiTools calling ProductImageAdminApiMethods");
		JSONProductImage imageResponse = imageAdminApiMethods.putEntity(payload, request, httpClient);
		System.out.println("AdminApiTools modified image " + imageResponse.getId());
		System.out.println("***********************************");

		return imageResponse;
		
	}
	

	private CloseableHttpClient getPostHttpClient() {
		RequestConfig globalConfig = RequestConfig.custom()
		        .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
		        .build();
		
		CloseableHttpClient httpClient = HttpClients.custom().disableCookieManagement()
		        .setDefaultRequestConfig(globalConfig)
		        .build();
		
		return httpClient;
	}
	
	public void setProductTransformer(JSONProductTransformer productTransformer) {
		this.productTransformer = productTransformer;
	}
	
	
	
}
