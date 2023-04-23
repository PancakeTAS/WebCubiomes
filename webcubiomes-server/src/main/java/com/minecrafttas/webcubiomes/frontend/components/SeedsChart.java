package com.minecrafttas.webcubiomes.frontend.components;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Marker;
import com.vaadin.flow.component.charts.model.PlotOptionsAreaspline;
import com.vaadin.flow.component.charts.model.PointPlacement;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class SeedsChart extends VerticalLayout {

	private HorizontalLayout header;
	private Chart chart;
	
	private H2 headerTitle;
	private Span headerSubtitle;
	private VerticalLayout headerColumn;
	
	public SeedsChart() {
		// Create header title
        this.headerTitle = new H2("Seeds");
        this.headerTitle.addClassNames(FontSize.XLARGE, Margin.NONE);

        // Create header subtitle
        this.headerSubtitle = new Span("seeds/day");
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
        
        // Create chart
        this.chart = new Chart(ChartType.AREASPLINE);
        
        // Configure chart
        var conf = this.chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.getyAxis().setTitle("Seeds");

        // Setup x axis
        var xAxis = new XAxis();
        xAxis.setCategories("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        conf.addxAxis(xAxis);

        // Setup plot options
        var plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        // Set values
        conf.addSeries(new ListSeries("Seeds checked", 189, 191, 291, 396, 501, 403, 609, 712, 729, 942, 1044, 1247));
        conf.addSeries(new ListSeries("Seeds found", 138, 246, 248, 348, 352, 353, 463, 573, 778, 779, 885, 887));
		
        // Add to main layout
		this.addClassName(Padding.LARGE);
        this.setPadding(false);
        this.setSpacing(false);
        this.getElement().getThemeList().add("spacing-l");
        this.add(this.header, this.chart);
	}
	
}
