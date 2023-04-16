package com.minecrafttas.webcubiomes.frontend.components;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.minecrafttas.webcubiomes.WebCubiomes;
import com.minecrafttas.webcubiomes.cubiomes.Condition;
import com.minecrafttas.webcubiomes.cubiomes.ProgressFile;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.UploadI18N.AddFiles;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

public class ConditionsPanel extends VerticalLayout {

	private H4 searchLabel;
	private Grid<Condition> conditionsGrid;
	
	private HorizontalLayout controlElements; // layout for the mc version, search mode and starting seed
	private HorizontalLayout controlButtons; // layout for the progress load/save/unload options
	
	private TextField mcField; // pointless combo box, could just be a text field but i like the arrow
	private TextField searchModeField;
	private TextField seedTextField;
	

	private Upload uploadButton;
	private Button downloadButton;
	private Button unloadButton;
	
	
	public ConditionsPanel() {
		// Initialize components
		this.searchLabel = new H4("Search Configuration");
		this.conditionsGrid = new Grid<>();
		this.mcField = new TextField("Minecraft Version");
		this.searchModeField = new TextField("Search Mode");
		this.seedTextField = new TextField("Starting Seed");
		//this.uploadButton = new Upload(...); below
		this.downloadButton = new Button("Save progress");
		this.unloadButton = new Button("Unload progress");
		
		// Setup grid
		this.conditionsGrid.setWidthFull();
		this.conditionsGrid.addColumn(item -> String.format("[%02d] %s", item.id(), item.type()));
		this.conditionsGrid.addColumn(Condition::area);
		this.conditionsGrid.setHeight("25vh");
		this.conditionsGrid.addClassName("monospace");
		
		// Make configuration elements read only
		this.mcField.setReadOnly(true);
		this.searchModeField.setReadOnly(true);
		this.seedTextField.setReadOnly(true);
		
		// Create upload button
		MemoryBuffer file = new MemoryBuffer(); // upload buffer
		this.uploadButton = new Upload(file);
		this.uploadButton.setDropAllowed(false); // hide drag/drop area
		this.uploadButton.setMaxFiles(1); // max 1 file
		this.uploadButton.setMaxFileSize((int) 2e+7);
		this.uploadButton.setI18n(new UploadI18N().setAddFiles(new AddFiles().setOne("Load progress"))); // change text
		this.uploadButton.getChildren().iterator().forEachRemaining(e -> {
			if (e instanceof Button)
				((Button) e).setWidthFull();
		}); // make button full width
		
		// Create layout for configuration elements
		this.controlElements = new HorizontalLayout(this.mcField, this.searchModeField, this.seedTextField);
		this.controlElements.setWidthFull();
		this.controlElements.setFlexGrow(1, this.mcField, this.searchModeField, this.seedTextField);
		
		// Create layout for save/load/unload buttons
		this.controlButtons = new HorizontalLayout(this.uploadButton, this.downloadButton, this.unloadButton);
		this.controlButtons.setWidthFull();
		this.controlButtons.setFlexGrow(1, this.uploadButton, this.downloadButton, this.unloadButton);
		
		// Add to main layout
		this.addClassName("simple-border");
		this.setFlexGrow(1, this.conditionsGrid);
		this.add(this.searchLabel, this.controlElements, this.conditionsGrid, this.controlButtons);
		
		// Upload action
		this.uploadButton.addSucceededListener(e -> {
			try {
				// try to parse progress file
				byte[] data = file.getInputStream().readNBytes((int) e.getContentLength());
				WebCubiomes.getInstance().loadProgressFile(ProgressFile.parseProgressFile(new String(data).split("\n")));
			} catch (Exception e1) {
				// don't care
			}
			// remove files below upload button
			this.uploadButton.clearFileList();
		});
		
		// Download action
		this.downloadButton.addClickListener(e -> {
			// Check if progress is even loaded
			var webcubiomes = WebCubiomes.getInstance();
			var progressFile = webcubiomes.getProgressFile();
			if (progressFile == null)
				return;
			
			// Create progress file download
			var lines = progressFile.updateProgressFile(webcubiomes.getProgress().get());
			var out = "";
			for (String l : lines)
				out += l + "\n";
			
			// Download progress file
			var page = this.getUI().get().getPage();
			page.executeJs("var a = document.createElement('a'); a.setAttribute('href', 'data:text/plain;charset=utf-8," + URLEncoder.encode(out, StandardCharsets.UTF_8).replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~") + "'); a.setAttribute('download', 'cubiomes.txt'); a.style.display = 'none'; document.body.appendChild(a); a.click(); document.body.removeChild(a);", this);
		});
		
		// Unload action
		this.unloadButton.addClickListener(e -> {
			WebCubiomes.getInstance().loadProgressFile(null); // just load nothing lol
		});
		
		// Add listener for progress file updates
		WebCubiomes.getInstance().registerListener(() -> {
			var webcubiomes = WebCubiomes.getInstance();
			var progressFile = webcubiomes.getProgressFile();
			
			// Load (or unload) the labels
			if (progressFile == null) {
				this.conditionsGrid.setItems(new Condition[0]);
				this.mcField.setValue("");
				this.searchModeField.setValue("");
				this.seedTextField.setValue("");
			} else {
				this.conditionsGrid.setItems(progressFile.conditions());
				this.mcField.setValue(progressFile.mc());
				this.searchModeField.setValue(progressFile.is48Bit() ? "48-bit family blocks" : "incremental");
				this.seedTextField.setValue(Long.toUnsignedString(progressFile.progress()));
			}
		});
	}
	
}
