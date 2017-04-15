package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormPanel extends JPanel implements ActionListener{
	
	private static final String FILENAME = "log/emails.txt";
	public int t = 0;
	public int timer = 500;
	public JTextField form;
	public JButton submit;
	public JLabel message, notice;
	public Font arial = new Font("Arial",0,20);
	public FormPanel() {
		this.setLayout(null);
		message = new JLabel("");
		notice = new JLabel("Please Enter your Email:");
		form = new JTextField(20);
		submit = new JButton("Submit");
		
		message.setFont(arial);
		notice.setFont(arial);
		form.setFont(arial);
		submit.setFont(arial);
		
		form.setBounds((int) Client.Screen.getWidth()/2-200, (int) Client.Screen.getHeight()/2, 400, 40);
		notice.setBounds((int) Client.Screen.getWidth()/2 - 430, (int) Client.Screen.getHeight()/2, 400, 40);
		submit.setBounds((int) Client.Screen.getWidth()/2 + 200, (int) Client.Screen.getHeight()/2, 100, 40);
		message.setBounds((int) Client.Screen.getWidth()/2-275, (int) Client.Screen.getHeight()/2 + 40, 800, 80);
		
		submit.addActionListener(this);
		form.addActionListener(this);
		this.add(notice);
		this.add(form);
		this.add(submit);
		this.add(message);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		//100,190,69 projx green
		g2.setColor(new Color(247, 249, 252));
		g2.fillRect(0, 0, Client.Screen.width, Client.Screen.height);
		g2.setColor(new Color(100,190,69));
		g2.setFont(new Font("Arial",0,80));
		g2.drawString("Raffle Signup", (int) (Client.Screen.getWidth()/2-200), 400);
		try {
			BufferedImage image = ImageIO.read(new File("img/projx-logo.png"));
		g2.drawImage(image,(int) Client.Screen.getWidth()/2 - image.getWidth()/2,30, null);
		} catch (IOException e ) {
			System.out.println("Could not read projx image");
		}
		
		form.repaint();
		submit.repaint();
		notice.repaint();
		message.repaint();
		if (t > 1) {
			t--;
			
		} else if (t == 1){
			message.setText("");
			t--;
		}
		g2.drawString("" + t, 100, 1000);
	}
	
	// taken from http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
	public static boolean isValidEmailAddress(String email) {
			System.out.println("function start");
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		      System.out.println("valid");
		   } catch (AddressException ex) {
		      result = false;
		      System.out.println("invalid");
		   }
		   return result;
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String email = form.getText().toLowerCase();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(FILENAME));
			try {
			    String line;

			    while ((line = br.readLine()) != null) {
			    	if (line.contentEquals(email)) {
			    		message.setText("Email response already recorded.");
			    		t = timer;
			    		br.close();
			    		return;
			    	}
			    }
			} finally {
			    br.close();
			}
		} catch (IOException err) {
			err.printStackTrace();
			message.setText("Could not find save file");
			t = timer;
		}

		// validate 
		
		if (email.endsWith("@mit.edu") && isValidEmailAddress(email)) {
			
			BufferedWriter bw = null;
			FileWriter fw = null;
			try {
			//append to the file
			fw = new FileWriter(FILENAME, true);
			bw = new BufferedWriter(fw);
			bw.write(email + "\n");
			} catch (IOException err) {
				message.setText("Error Saving Email, please try again.");
				t = timer;

			} finally {
				try {
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			form.setText("");
			message.setText("Email saved, you will be notified if you win,\n stick around to claim your prize!");
			t = timer;
			}
		} else {
			message.setText("Sorry not a valid email, must end with @mit.edu");
			t = timer;
		}
	}
}
