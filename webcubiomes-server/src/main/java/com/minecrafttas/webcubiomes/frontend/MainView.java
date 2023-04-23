package com.minecrafttas.webcubiomes.frontend;

import com.minecrafttas.webcubiomes.frontend.layouts.MainLayout;
import com.minecrafttas.webcubiomes.frontend.layouts.StatsLayout;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Main WebCubiomes View
 * @author Pancake
 */
@PageTitle("Web Cubiomes")
@Route(value = "")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
public class MainView extends HorizontalLayout {

	private MainLayout mainLayout;
	private StatsLayout statsLayout;
	
	public MainView() {
		this.mainLayout = new MainLayout();
		this.mainLayout.addClassName("main-layout");
		this.statsLayout = new StatsLayout();
		this.statsLayout.addClassName("dashboard-view");
		
		// Removing padding/spacing instead use inner layout pd/sp
		this.setPadding(false);
		this.setSpacing(false);
		this.add(this.mainLayout, this.statsLayout);
	}
	
}
