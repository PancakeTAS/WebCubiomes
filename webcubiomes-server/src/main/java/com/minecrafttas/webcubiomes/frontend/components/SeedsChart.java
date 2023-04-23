package com.minecrafttas.webcubiomes.frontend.components;

import com.minecrafttas.webcubiomes.WebCubiomes;
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
        this.headerSubtitle = new Span("seeds/time");
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
        conf.addxAxis(xAxis);

        // Setup plot options
        var plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        // Add to main layout
		this.addClassName(Padding.LARGE);
        this.setPadding(false);
        this.setSpacing(false);
        this.getElement().getThemeList().add("spacing-l");
        this.add(this.header, this.chart);
        
        // Add Listener
		WebCubiomes.getInstance().registerListener(() -> {
			var file = WebCubiomes.getInstance().getProgressFile();
			if (file == null) {
				xAxis.setCategories();
				conf.setSeries();
			} else {
				var history = file.statistics().getHistory();
				
				int i = history.size() / 10;
				if (i == 0)
					xAxis.setCategories("1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m", "10m");
				else if (i == 1)
					xAxis.setCategories("5m", "10m", "15m", "20m", "25m", "30m", "35m", "40m", "45m", "50m");
				else if (i == 2)
					xAxis.setCategories("1h", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h");
				else
					xAxis.setCategories("1d", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "10d");
				
				i*= 10;
				conf.addSeries(new ListSeries("Seeds checked", history.subList(i, Math.min(i + 10, history.size())).toArray(Long[]::new)));
			}
		});
	}
	
}
