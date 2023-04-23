package com.minecrafttas.webcubiomes.frontend.layouts;

import com.minecrafttas.webcubiomes.WebCubiomes;
import com.minecrafttas.webcubiomes.frontend.components.Highlight;
import com.minecrafttas.webcubiomes.frontend.components.ProgressGrid;
import com.vaadin.flow.component.board.Board;

public class StatsLayout extends Board {
	
	private Highlight seedsChecked;
	private Highlight seedsFound;
	private Highlight activeClients;
	private Highlight timeSearched;
	
	private ProgressGrid progressGrid;
	
	public StatsLayout() {
		// Create highlights
		this.seedsChecked = new Highlight("Seeds checked", "0");
		this.seedsFound = new Highlight("Seeds found", "0");
		this.activeClients = new Highlight("Active clients", "0");
		this.timeSearched = new Highlight("Time spent searching", "0:0:0");
		
		// Create progress row
		this.progressGrid = new ProgressGrid();
		
		// Add to main layout
		this.setHeight("100vh");
		this.addRow(this.seedsChecked, this.seedsFound, this.activeClients, this.timeSearched);
		this.addRow(this.progressGrid).addClassName("noborder");
		
		// Add listener
		WebCubiomes.getInstance().registerListener(() -> {
			var file = WebCubiomes.getInstance().getProgressFile();
			if (file == null) {
				this.seedsChecked.setValue("0");
				this.seedsFound.setValue("0");
				this.activeClients.setValue("0");
				this.timeSearched.setValue("00:00:00");
			} else {
				var stats = file.statistics();
				this.seedsChecked.setValue(Long.toUnsignedString(stats.getSeedsChecked()));
				this.seedsFound.setValue(Long.toUnsignedString(stats.getSeedsFound()));
				this.activeClients.setValue(stats.getActiveClients() + "");
				this.timeSearched.setValue(stats.getTimeWorkedOn());
			}
		});
	}

}
