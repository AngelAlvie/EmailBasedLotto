package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormPanel extends JPanel implements ActionListener{
	
	private static final String FILENAME = "log/emails.txt";
	
	public JTextField form;
	public JButton submit;
	public FormPanel() {
		form = new JTextField("Enter your Email");
		submit = new JButton("Submit");
		submit.addActionListener(this);
		this.add(form);
		this.add(submit);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//100,190,69 projx green
		g2.setColor(new Color(255,255,255));
		g2.fillRect(0, 0, Client.Screen.width, Client.Screen.height);;
		try {
		g2.drawImage(ImageIO.read(new File("img/projx-logo.svg")), 10, 10, null);
		} catch (IOException e ) {
			System.out.println("Could not read projx image");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String email = form.getText();
		if (email.endsWith("@mit.edu")) {
			BufferedWriter bw = null;
			FileWriter fw = null;
			try {
			//append to the file
			fw = new FileWriter(FILENAME, true);
			bw = new BufferedWriter(fw);
			bw.write(email);

			System.out.println("Done");

			} catch (IOException err) {

				err.printStackTrace();

			} finally {
				try {
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			
		}
	}
}
