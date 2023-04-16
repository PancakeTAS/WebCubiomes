package com.minecrafttas.webcubiomes.frontend.components;

import java.util.ArrayList;

import com.minecrafttas.webcubiomes.WebCubiomes;
import com.minecrafttas.webcubiomes.cubiomes.Seed;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SeedsPanel extends VerticalLayout {

	private H4 conditionsLabel;
	private Grid<Seed> seedsGrid;
	
	private HorizontalLayout controlButtons; // layout for the 4 buttons below
	
	private Button clearButton;
	private Button removeButton;
	private Button removeStructureButton;
	private Button openButton;
	
	public SeedsPanel() {
		// Initialize components
		this.seedsGrid = new Grid<>();
		this.conditionsLabel = new H4("Seeds");
		this.clearButton = new Button("Clear Seeds");
		this.removeButton = new Button("Remove Seed");
		this.removeStructureButton = new Button("Remove Structure Seed");
		this.openButton = new Button("Open seed");
		
		// Setup grid
		this.seedsGrid.setWidthFull();
		this.seedsGrid.addColumn(Seed::seed).setHeader("Seed").setAutoWidth(true);
		this.seedsGrid.addColumn(Seed::top16).setHeader("Biome Seed").setAutoWidth(true);
		this.seedsGrid.addColumn(Seed::lower48).setHeader("Structure Seed").setAutoWidth(true);
		this.seedsGrid.setHeight("20vh");
		this.seedsGrid.addClassName("monospace");

		// Disable buttons because grid is empty
		this.removeButton.setEnabled(false);
		this.removeStructureButton.setEnabled(false);
		this.openButton.setEnabled(false); // disabled cuz wip
		
		// Create layout for the 4 buttons
		this.controlButtons = new HorizontalLayout(this.clearButton, this.removeButton, this.removeStructureButton, this.openButton);
		this.controlButtons.setWidthFull();
		this.controlButtons.setFlexGrow(1, this.clearButton, this.removeButton, this.removeStructureButton, this.openButton);
		
		// Add to main layout
		this.addClassName("simple-border");
		this.setFlexGrow(1, this.seedsGrid);
		this.add(this.conditionsLabel, this.seedsGrid, this.controlButtons);
		
		// Add selection listener to enable/disable buttons
		this.seedsGrid.addSelectionListener(e -> {
			// enable/disable buttons if grid selection present
			var isPresent = e.getFirstSelectedItem().isPresent();
			this.removeButton.setEnabled(isPresent);
			this.removeStructureButton.setEnabled(isPresent);
		});

		// Remove button action
		this.removeButton.addClickListener(e -> {
			// remove seed and select previous item
			var selected = this.seedsGrid.getSelectedItems().iterator().next();
			var seeds = WebCubiomes.getInstance().getProgressFile().seeds();
			var index = seeds.indexOf(selected);
			seeds.remove(selected);
			if (!seeds.isEmpty()) {
				this.seedsGrid.select(seeds.get(Math.max(0, index-1)));
			} else { // disable buttons of no previous item
				this.removeButton.setEnabled(false);
				this.removeStructureButton.setEnabled(false);
			}
			
			this.seedsGrid.getDataProvider().refreshAll();
		});
		
		// Remove structure seed button action
		this.removeStructureButton.addClickListener(e -> {
			// get structure seed and oh boy this line is very looooooooooooooooooooooooooong
			var structureSeed = this.seedsGrid.getSelectedItems().iterator().next().lower48();
			var seeds = WebCubiomes.getInstance().getProgressFile().seeds();
			
			// remove all seeds with same structure seed
			for (var seed : new ArrayList<>(seeds))
				if (seed.lower48().equalsIgnoreCase(structureSeed))
					seeds.remove(seed);
			
			// disable buttons because no grid selection
			this.removeButton.setEnabled(false);
			this.removeStructureButton.setEnabled(false);
			this.seedsGrid.getDataProvider().refreshAll();
		});
		
		// Clear button action
		this.clearButton.addClickListener(e -> {
			var webcubiomes = WebCubiomes.getInstance();
			var progressFile = webcubiomes.getProgressFile();
			if (progressFile == null)
				return;
			
			// clear grid and disable buttons
			progressFile.seeds().clear();
			this.removeButton.setEnabled(false);
			this.removeStructureButton.setEnabled(false);
			this.seedsGrid.getDataProvider().refreshAll();
		});
		
		// Add listener for progress file updates
		WebCubiomes.getInstance().registerListener(() -> {
			var webcubiomes = WebCubiomes.getInstance();
			var progressFile = webcubiomes.getProgressFile();
			
			// update grid items
			if (progressFile == null) {
				this.seedsGrid.setItems(new Seed[0]);
			} else {
				var seeds = progressFile.seeds();
				this.seedsGrid.select(null);
				this.seedsGrid.setItems(seeds);
			}
			
			// disable buttons because no grid selection
			this.removeButton.setEnabled(false);
			this.removeStructureButton.setEnabled(false);
		});
	}
	
}
