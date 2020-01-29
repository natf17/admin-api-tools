package com.ppublica.shopify.admin.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppublica.shopify.admin.json.DefaultJSONProduct;
import com.ppublica.shopify.admin.json.JSONProductTransformer;
import com.ppublica.shopify.admin.json.artcb.JSONProductArtCB;

public class JSONFileTools {
	
	private JSONProductTransformer transformer;
	
	public JSONFileTools() {
		this.transformer = new JSONProductTransformer();
	}
	
	public List<DefaultJSONProduct> readJSONProducts(String pathToResource) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		BufferedReader reader = null;
		JSONProducts wrapper = null;
		
		try {
			reader = new BufferedReader(new FileReader(pathToResource));

			try {
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				
				wrapper = mapper.readValue(reader, JSONProducts.class);
			} catch(JsonParseException ex) {
				System.out.println("JsonParseException");
				throw ex;
			} catch(JsonMappingException ex) {
				System.out.println("JsonMappingException");
				throw ex;
			}
			
			
		} catch(IOException ex) {
			System.out.println("IO EXCEPTION - Could not open file " + pathToResource);
			throw ex;
		} finally {
			if(reader!= null) {
				reader.close();
			}
		}
		
		if(wrapper != null) {
			
			return transformer.asDefaultProducts(wrapper.getProducts());
		}
		
		return null;
		
	}
	
	public static class JSONProducts {
		private List<JSONProductArtCB> products;
		
		public void setProducts(List<JSONProductArtCB> products) {
			this.products = products;
		}

		
		public List<JSONProductArtCB> getProducts() {
			return this.products;
		}
	}
	

}
