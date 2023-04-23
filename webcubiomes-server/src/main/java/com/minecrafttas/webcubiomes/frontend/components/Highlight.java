package com.minecrafttas.webcubiomes.frontend.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class Highlight extends VerticalLayout {

	private H2 title;
	private Span value;
	private Span badge;
	
	private Icon badgeIcon;
	private Span badgeText;
	
	public Highlight(String title, String value, double percentage) {
		// Create title
		this.title = new H2(title);
		this.title.addClassNames(FontWeight.NORMAL, Margin.NONE, TextColor.SECONDARY, FontSize.XSMALL);
		
		// Create value
		this.value = new Span(value);
		this.value.addClassNames(FontWeight.SEMIBOLD, FontSize.XXXLARGE);
		
		// Create badge
        var icon = VaadinIcon.ARROW_UP;
        var prefix = "";
        var theme = "badge";

        if (percentage == 0) {
            prefix = "±";
        } else if (percentage > 0) {
            prefix = "+";
            theme += " success";
        } else if (percentage < 0) {
            icon = VaadinIcon.ARROW_DOWN;
            theme += " error";
        }

        this.badgeIcon = icon.create();
        this.badgeIcon.addClassNames(BoxSizing.BORDER, Padding.XSMALL);

        this.badgeText = new Span(prefix + percentage);

		this.badge = new Span(this.badgeIcon, this.badgeText);
		this.badge.getElement().getThemeList().add(theme);
		
		// Add to main layout
        this.addClassName(Padding.LARGE);
        this.setPadding(false);
        this.setSpacing(false);
        this.add(this.title, this.value, this.badge);
	}
	
}
