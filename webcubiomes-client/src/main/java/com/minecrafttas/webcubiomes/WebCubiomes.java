package com.minecrafttas.webcubiomes;

import javax.swing.JFrame;

public class WebCubiomes extends JFrame {
	public static int THREADS = 32;
	
	public WebCubiomes() {
		super("Test");
		this.setBounds(200, 200, 800, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
