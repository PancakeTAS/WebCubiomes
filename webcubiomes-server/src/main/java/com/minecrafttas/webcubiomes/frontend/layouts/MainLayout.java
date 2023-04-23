package com.minecrafttas.webcubiomes.frontend.layouts;

import com.minecrafttas.webcubiomes.frontend.components.ConditionsPanel;
import com.minecrafttas.webcubiomes.frontend.components.SeedsPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainLayout extends VerticalLayout {

	private ConditionsPanel conditionsPanel;
	private SeedsPanel seedsPanel;
	
	public MainLayout() {
		this.conditionsPanel = new ConditionsPanel();
		this.seedsPanel = new SeedsPanel();
		
		this.setFlexGrow(1, this.seedsPanel);
		this.setFlexShrink(1, this.conditionsPanel);
		this.setHeight("100vh");
		this.add(this.conditionsPanel, this.seedsPanel);
	}
	
}
