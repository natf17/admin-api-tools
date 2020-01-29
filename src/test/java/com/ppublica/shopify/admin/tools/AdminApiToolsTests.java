package com.ppublica.shopify.admin.tools;

import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.JSONProductTransformer;
import com.ppublica.shopify.admin.json.artcb.LMProductTransformer;

public class AdminApiToolsTests {
	
	String shopRootPath;
	String username;
	String password;
	
	@Before
	public void setup() throws FileNotFoundException, IOException {
		 Properties properties = new Properties();
		 properties.load(new BufferedReader(new FileReader("/Users/nathanaelfarciert/Desktop/LM-data/store_info.properties")));
		 
		 this.shopRootPath = properties.getProperty("shopRootPath");
		 this.username = properties.getProperty("username");
		 this.password = properties.getProperty("password");
		 
	}
	
	
	
	//@Test
	public void postEngagementRingsProductSuccess() throws IOException {
		// read an object from the file and post
		ShopifyAdminInfo mockAdminInfo = new ShopifyAdminInfo(this.shopRootPath, this.username, this.password);
		
		AdminApiTools tools = new AdminApiTools(mockAdminInfo);
		
		LMProductTransformer customTransformer = new LMProductTransformer();
		customTransformer.setLMCategory("engagement-rings", "$f$bridalcat$Engagement Rings");
		customTransformer.addCustomTag("art_carved_bridal");
		customTransformer.setLMProductType("ArtCarved Bridal");
		
		JSONProductTransformer productTransformer = new JSONProductTransformer();
		
		productTransformer.setLMProductTransformer(customTransformer);
		
		tools.setProductTransformer(productTransformer);
		
		
		//URL url = Object.class.getResource("/sample.txt");	
		String path1 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/engagement-rings_1";
		String path2 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/engagement-rings_2";
		String path3 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/engagement-rings_3";
		String path4 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/engagement-rings_4";
		String path5 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/engagement-rings_5";

		List<DefaultJSONProduct> products = new JSONFileTools().readJSONProducts(path5);
		
		//tools.postProducts(products);
		
		//List<DefaultJSONProduct> resp = tools.postProducts(products);
		
		//Assert.assertNotNull(resp);
	}
	
	@Test
	public void postMensWeddingBandsProductSuccess() throws IOException {
		// read an object from the file and post
		ShopifyAdminInfo mockAdminInfo = new ShopifyAdminInfo(this.shopRootPath, this.username, this.password);
		
		AdminApiTools tools = new AdminApiTools(mockAdminInfo);
		
		LMProductTransformer customTransformer = new LMProductTransformer();
		customTransformer.setLMCategory("mens-wedding-bands", "$f$bridalcat$Mens Wedding Bands");
		customTransformer.addCustomTag("art_carved_bridal");
		customTransformer.setLMProductVendor("ArtCarved Bridal");
		
		JSONProductTransformer productTransformer = new JSONProductTransformer();
		
		productTransformer.setLMProductTransformer(customTransformer);
		
		tools.setProductTransformer(productTransformer);
		
		
		String path1 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/mens-wedding-bands_1";
		String path2 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/mens-wedding-bands_2";
		String path3 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/mens-wedding-bands_3";
		String path4 = "/Users/nathanaelfarciert/Desktop/LM-data/art_carved_bridal/mens-wedding-bands_4";

		List<DefaultJSONProduct> products = new JSONFileTools().readJSONProducts(path1);
		
		DefaultJSONProduct prod = tools.postProduct(products.get(0));
		System.out.println(prod);
		//tools.postProducts(products);
		
		//List<DefaultJSONProduct> resp = tools.postProducts(products);
		
		//Assert.assertNotNull(resp);
	}
}
