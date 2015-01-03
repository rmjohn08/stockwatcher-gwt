package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.sample.stockwatcher.client.content.Login;
import com.google.gwt.user.client.ui.RootPanel;

public class UiBinderLogin implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		RootPanel.get().add(new Login());
	}

}
