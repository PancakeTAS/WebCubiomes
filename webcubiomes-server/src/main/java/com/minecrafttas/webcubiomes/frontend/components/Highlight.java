package com.minecrafttas.webcubiomes.frontend.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class Highlight extends VerticalLayout {

	private H2 title;
	private Span value;
	
	public Highlight(String title, String value) {
		// Create title
		this.title = new H2(title);
		this.title.addClassNames(FontWeight.NORMAL, Margin.NONE, TextColor.SECONDARY, FontSize.XSMALL);
		
		// Create value
		this.value = new Span(value);
		this.value.addClassNames(FontWeight.SEMIBOLD, FontSize.XXXLARGE);

		// Add to main layout
        this.addClassName(Padding.LARGE);
        this.setPadding(false);
        this.setSpacing(false);
        this.add(this.title, this.value);
	}
	
	public void setValue(String value) {
		this.value.setText(value);
	}
	
}
