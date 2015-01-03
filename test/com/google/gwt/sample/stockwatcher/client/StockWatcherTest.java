package com.google.gwt.sample.stockwatcher.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class StockWatcherTest extends GWTTestCase {

	@Test
	public void testStockPriceCtor() {  
		String symbol = "XYZ";  
		double price = 70.0;  
		double change = 2.0;  
		double changePercent = 100.0 * change / price;
		StockPrice sp = new StockPrice(symbol, price, change);  
		assertNotNull(sp);  
		assertEquals(symbol, sp.getSymbol());  
		assertEquals(price, sp.getPrice(), 0.001);  
		assertEquals(change, sp.getChange(), 0.001);  
		assertEquals(changePercent, sp.getChangePercent(), 0.001); 
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "com.google.gwt.sample.stockwatcher.StockWatcher";
	}
	
	

}
