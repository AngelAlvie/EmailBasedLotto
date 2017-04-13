package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Client extends JFrame {
	
	FormPanel P;
	static Dimension Screen;
	
	public Client() {
		this.setResizable(true);
		
		//add panels here
		 Screen = Toolkit.getDefaultToolkit().getScreenSize();
		 
		P = new FormPanel();
		this.add(P);
		
		this.setTitle("Galaxy's Edge");
		this.setResizable(false);
		this.setSize(Screen);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setIconImage(ImageIO.read(new File("img/logo.png")));
		} catch (IOException e) {
			System.out.print("No icon file found.");
		}
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		
		//after all window properties are set and all components are added: 
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Client c = new Client();
	}
	
}