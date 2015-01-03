package com.google.gwt.sample.stockwatcher.client.content;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class StockShell extends ResizeComposite {

	@UiTemplate("StockShell.ui.xml")
	interface StockShellBinder extends UiBinder<Widget, StockShell> {
	}

	private static UiBinder<Widget, StockShell> binder = GWT
			.create(StockShellBinder.class);

	/* @UiField
	Label logoLabel;
	*/

	@UiField
	FlowPanel westPanel;
	
	@UiField
	FlowPanel centerPanel;
	
	public StockShell() {
		initWidget(binder.createAndBindUi(this));
		westPanel.add(new Login());	
		
		StockPanelContent stock = new StockPanelContent(centerPanel);
		stock.loadStockWatcher();
		
		/* if (Window.confirm("Delete center ?")) {
			centerPanel.clear();
		} */ 
		
	}

}
