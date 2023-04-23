package com.minecrafttas.webcubiomes.frontend.components;

import com.minecrafttas.webcubiomes.WebCubiomes;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class ProgressGrid extends VerticalLayout {

	private record Room(String room, String progress, String progressInPercentage) {}
	
	private HorizontalLayout header;
	private Grid<Room> grid;
	
	private H2 headerTitle;
	private Span headerSubtitle;
	private VerticalLayout headerColumn;
	
	public ProgressGrid() {
		// Create header title
        this.headerTitle = new H2("Room progress");
        this.headerTitle.addClassNames(FontSize.XLARGE, Margin.NONE);

        // Create header subtitle
        this.headerSubtitle = new Span("Structure seed rooms");
        this.headerSubtitle.addClassNames(TextColor.SECONDARY, FontSize.XSMALL);

        // Create header column
        this.headerColumn = new VerticalLayout();
        this.headerColumn.setPadding(false);
        this.headerColumn.setSpacing(false);
        this.headerColumn.add(this.headerTitle, this.headerSubtitle);
     
        // Create header
        this.header = new HorizontalLayout();
        this.header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        this.header.setSpacing(false);
        this.header.setWidthFull();
        this.header.add(this.headerColumn);
		
        // Create grid
        this.grid = new Grid<>();
        this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.grid.setHeight("76vh");
        this.grid.addColumn(Room::room).setHeader("Room").setAutoWidth(true);
        this.grid.addColumn(Room::progress).setHeader("Progress").setAutoWidth(true);
        this.grid.addColumn(Room::progressInPercentage).setHeader("Progress (in %)").setAutoWidth(true);
        
        // Add to main layout
		this.addClassName(Padding.LARGE);
        this.setPadding(false);
        this.setSpacing(false);
        this.add(this.header, this.grid);
	
		// Add listener
		WebCubiomes.getInstance().registerListener(() -> {
			var file = WebCubiomes.getInstance().getProgressFile();
			if (file == null) {
				this.grid.setItems(new Room[0]);
			} else {
				var progress = file.progress().getProgress();
				var items = new Room[progress.length];
				for (int i = 0; i < progress.length; i++)
					items[i] = new Room("0x" + String.format("%02x", i).toUpperCase(), "0x" + Long.toHexString(progress[i]).toUpperCase(), ((int) (((double) progress[i] / (double) 0xFFFFFFFFFFL) * 100000) / 100.0D) + "%");
				this.grid.setItems(items);
			}
		});
	}
	
}
