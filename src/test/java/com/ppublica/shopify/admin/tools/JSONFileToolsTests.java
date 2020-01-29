package com.ppublica.shopify.admin.tools;

import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;


public class JSONFileToolsTests {
	
	private JSONFileTools tools;
	
	@Before
	public void setup() {
		this.tools = new JSONFileTools();
	}
	

	@Test
	public void readJSONProductsSuccess() throws Exception {
		URL url = Object.class.getResource("/sample.txt");
		String path = url.getPath();
		System.out.println(path);
		
		List<DefaultJSONProduct> products = tools.readJSONProducts(path);
		
		Assert.assertEquals(30, products.size());
		
	}
}
