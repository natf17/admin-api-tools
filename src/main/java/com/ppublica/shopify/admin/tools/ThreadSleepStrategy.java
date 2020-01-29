package com.ppublica.shopify.admin.tools;

public class ThreadSleepStrategy {
	private static final long SLEEP_SECONDS = 2L;
	
	public void sleep() {
		try {
			System.out.println("*****------------ SLEEPING ------------*****");
			Thread.sleep(SLEEP_SECONDS);
			
		} catch(InterruptedException ex) {
			throw new RuntimeException(ex);
		}
		
		System.out.println("*****------------ WAKING ------------*****");
	}

}
