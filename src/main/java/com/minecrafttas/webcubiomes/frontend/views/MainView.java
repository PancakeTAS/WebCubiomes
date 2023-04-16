package com.minecrafttas.webcubiomes.frontend.views;

import com.minecrafttas.webcubiomes.frontend.components.MainLayout;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Main WebCubiomes View
 * @author Pancake
 */
@PageTitle("Main")
@Route(value = "")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
public class MainView extends HorizontalLayout {

	private MainLayout mainLayout;
	
	public MainView() {
		this.mainLayout = new MainLayout();
		this.mainLayout.addClassName("main-layout");
		
		// Removing padding/spacing instead use inner layout pd/sp
		this.setPadding(false);
		this.setSpacing(false);
		this.add(this.mainLayout);
	}
	
}
