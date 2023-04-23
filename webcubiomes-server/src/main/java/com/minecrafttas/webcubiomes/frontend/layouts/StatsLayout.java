package com.minecrafttas.webcubiomes.frontend.layouts;

import com.minecrafttas.webcubiomes.frontend.components.Highlight;
import com.minecrafttas.webcubiomes.frontend.components.ProgressGrid;
import com.minecrafttas.webcubiomes.frontend.components.SeedsChart;
import com.vaadin.flow.component.board.Board;

public class StatsLayout extends Board {
	
	private Highlight seedsChecked;
	private Highlight seedsFound;
	private Highlight activeClients;
	private Highlight timeSearched;
	
	private SeedsChart seedsChart;
	private ProgressGrid progressGrid;
	
	public StatsLayout() {
		// create highlights
		this.seedsChecked = new Highlight("Seeds checked", "0", 2.0);
		this.seedsFound = new Highlight("Seeds found", "0", 2.0);
		this.activeClients = new Highlight("Active clients", "0", 2.0);
		this.timeSearched = new Highlight("Time spent searching", "0", 2.0);
		
		// create seed and progress rows
		this.seedsChart = new SeedsChart();
		this.progressGrid = new ProgressGrid();
		
		this.setHeight("100vh");
		this.addRow(this.seedsChecked, this.seedsFound, this.activeClients, this.timeSearched);
		this.addRow(this.seedsChart);
		this.addRow(this.progressGrid).addClassName("noborder");
	}

}
