package com.ppublica.shopify.admin.tools;

import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicHeader;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Creates RequestBase objects for different Admin scenarios. This class contains Shopify-specific and store-specific
 * information, such as paths and credentials.
 * 
 * <p>NOT THREAD SAFE</p>
 */
public class ShopifyAdminInfo {
	private ThreadSleepStrategy sleepStrategy = new ThreadSleepStrategy();
	private final String shopAdminPath;
	private final String postProductPath;
	private final String putImagePathTemplate;
	private final String usernamePasswordBase64;
	private final long bucketSize = 40L;
	private final long leakRatePerSecond = 2L;
	private long current_bucket_size = 0L;
	private long time_bucket_leaked = 0L;
	
	public ShopifyAdminInfo(String shopRootPath, String username, String password) {
		// build paths
		this.shopAdminPath = shopRootPath + "/admin/api/2019-10";
		this.postProductPath = shopAdminPath + "/products.json";
		this.putImagePathTemplate = shopAdminPath + "/products/{productId}/images/{imageId}.json";
		this.usernamePasswordBase64 = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}
	
	public void setSleepStrategy(ThreadSleepStrategy strategy) {
		this.sleepStrategy = strategy;
	}


	public HttpEntityEnclosingRequestBase getPostProductRequestBase() {
		HttpEntityEnclosingRequestBase request = new HttpPost(this.postProductPath);
		
		// add headers
		request.addHeader(new BasicHeader("Authorization", "Basic " + usernamePasswordBase64));
		request.addHeader("Content-type", "application/json");

		
		return request;
	}
	
	public HttpEntityEnclosingRequestBase getPutImageRequestBase(Long productId, Long imageId) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("productId", productId.toString());
		uriVariables.put("imageId", imageId.toString());
		
		
		String putImagePath = UriComponentsBuilder.fromUriString(this.putImagePathTemplate)
				.buildAndExpand(uriVariables)
				.toUriString();
		
		HttpEntityEnclosingRequestBase request = new HttpPut(putImagePath);
		
		// add headers
		request.addHeader(new BasicHeader("Authorization", "Basic " + usernamePasswordBase64));
		request.addHeader("Content-type", "application/json");

		
		return request;	
		
	}
	
	/**
	 * Method that sleeps the thread for one second if the bucket is full. This ensures that after this method is called,
	 * the application can successfully interact with the Admin API. Upon invocation, the current "bucket" size will be
	 * updated. This method should only be called prior to a request to the Admin API.
	 */
	protected void checkBucket() {
		
		long now = Instant.now().getEpochSecond(); 
		System.out.println("::::current_bucket_size: " + current_bucket_size);
		
		// "initialize bucket"
		if(time_bucket_leaked == 0) {
			time_bucket_leaked = now;
		}
		
		// should current bucket leak?
		long secondsElapsedSinceLastLeak = now - time_bucket_leaked;
		
		long bucketOutflow = 0L;
		if(secondsElapsedSinceLastLeak >= 1) {
			bucketOutflow = secondsElapsedSinceLastLeak * leakRatePerSecond;
			System.out.println("::::time to update bucket");
			removeFromBucketAndReset(bucketOutflow, now);

		}	
		
		if(current_bucket_size < bucketSize) {
			System.out.println("::::Not sleeping! ");
			current_bucket_size++;
			System.out.println("::::current_bucket_size with request: " + current_bucket_size);
			return;
		} else {
			sleepStrategy.sleep();
			checkBucket();
		}
		
		
	}
	
	private void removeFromBucketAndReset(long quantityLeaked, long leakTime) {
		System.out.println("::::quantityLeaked: " + quantityLeaked);
		System.out.println("::::new leak time: " + leakTime);

		current_bucket_size = current_bucket_size - quantityLeaked;
		time_bucket_leaked = leakTime;
		
		if(current_bucket_size < 0) {
			current_bucket_size = 0;
		}
		System.out.println("::::current_bucket_size: " + current_bucket_size);


	}
	
	

}
