package com.google.gwt.sample.stockwatcher.client.content;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.sample.stockwatcher.client.StockPrice;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StockPanelContent {

private static final int REFRESH_INTERVAL = 5000; //ms
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addStockButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();
	
	private ArrayList<String> stocks = new ArrayList<String>();
	
	private Panel widget;
	public StockPanelContent(Panel w) {
		widget = w;
	}
	public void loadStockWatcher() {
		// create a table for stock data 
		stocksFlexTable.setText(0,0, "Symbol");
		stocksFlexTable.setText(0,1, "Price");
		stocksFlexTable.setText(0,2, "Change");
		stocksFlexTable.setText(0,3, "Remove");
		
		// assemble add stock panel
		addPanel.add(newSymbolTextBox);
		addPanel.add(addStockButton);
		
		// assemble Main panel
		mainPanel.add(stocksFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);
		
		// associate the main panel to the html
		//RootPanel.get("stockList").add(mainPanel);
		widget.add(mainPanel);
		
		//widget.remove(mainPanel);
		
		// move cursor focus to the input box
		newSymbolTextBox.setFocus(true);
		
		//setup the timer
		Timer refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshWatchList();
			}
		};
		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		
		
		//listen for mouse events
		addStockButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addStock();
				
			}
			
		});
		
		newSymbolTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addStock();
					
				}
			}
		});
		
		stocksFlexTable.getRowFormatter().addStyleName(0,"watchListHeader");
		stocksFlexTable.addStyleName("watchList");
	    stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	    stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

	    addPanel.addStyleName("addPanel");
	}
	
	private void refreshWatchList() {
		// todo
		final double MAX_PRICE = 100.0;		// $100.00	
		final double MAX_PRICE_CHANGE = 0.02; // +/- 2%
		
		StockPrice[] prices = new StockPrice[stocks.size()];
		for (int i = 0; i < stocks.size(); i++) {
			double price = Random.nextDouble() * MAX_PRICE;
			double change = price * MAX_PRICE_CHANGE * 
					(Random.nextDouble() * 2.0 - 1.0 );
			
			prices[i] = new StockPrice(stocks.get(i), price, change);
			
		}
		
		updateTable(prices);
		
		
	}
	
	private void updateTable(StockPrice[] prices) {
		for (int i = 0; i<prices.length; i++) {
			updateTable(prices[i]);
			
		}
		
		lastUpdatedLabel.setText("Last update :" + 
		DateTimeFormat.getMediumDateTimeFormat().format(new Date()));
		
	}
	
	/**
	   * Update a single row in the stock table.
	   *
	   * @param price Stock data for a single row.
	   */
	  private void updateTable(StockPrice price) {
	    // Make sure the stock is still in the stock table.
	    if (!stocks.contains(price.getSymbol())) {
	      return;
	    }

	    int row = stocks.indexOf(price.getSymbol()) + 1;

	    // Format the data in the Price and Change fields.
	    String priceText = NumberFormat.getFormat("#,##0.00").format(
	        price.getPrice());
	    NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
	    String changeText = changeFormat.format(price.getChange());
	    String changePercentText = changeFormat.format(price.getChangePercent());

	    // Populate the Price and Change fields with new data.
	    stocksFlexTable.setText(row, 1, priceText);
	    stocksFlexTable.setText(row, 2, changeText + " (" + changePercentText
	        + "%)");
	  }
	  
	private void addStock() {
		// 
		final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		newSymbolTextBox.setFocus(true);
		
		if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			Window.alert("'" + symbol + "' is not a valid symbol. Please review your input");
			newSymbolTextBox.selectAll();
			return;
			
		}
		newSymbolTextBox.setText("");
		/* todo : dont add the stock if already in the table
		 * 		add stock to the table
		 * 		add a button to remove this stock from table
		 *		get the stock price
		 */
		
		if (stocks.contains(symbol))
			return;
		
		int row = stocksFlexTable.getRowCount();
		stocks.add(symbol);
		stocksFlexTable.setText(row,0,symbol);
		stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
	    stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
	    stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

	    
		// add button to remove row
		Button removeStockButton = new Button("x");
		removeStockButton.addStyleDependentName("remove");
		removeStockButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int removedIndex = stocks.indexOf(symbol);
				stocks.remove(removedIndex);
				stocksFlexTable.removeRow(removedIndex + 1);
			}
		});
		stocksFlexTable.setWidget(row,  3, removeStockButton);
		
	}
	
	
}
