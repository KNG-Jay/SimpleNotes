package simpleNotes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SimpleNotesTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Simple Notes - A Note Taking Application");
		// FIX ME: Add background photo of JFrame
		MyJPanel jp = new MyJPanel();
		jp.setBounds(0, 0, 700, 270);
		frame.add(jp);
		frame.setBackground(Color.CYAN);
		
		JLabel label = new JLabel("Notes");
		label.setBounds(300, 50, 200, 200);
		label.setFont(new Font("Monospaced", Font.BOLD, 30));
		label.setForeground(Color.RED);
		
		JLabel subject = new JLabel("Subject:");
		subject.setBounds(130, 200, 200, 200);
		subject.setFont(new Font("Serif", Font.BOLD, 22));
		subject.setForeground(Color.RED);
		JTextField subjectField = new JTextField();
		subjectField.setBounds(250, 287, 250, 30);
		
		JLabel title = new JLabel("Title:");
		title.setBounds(130, 300, 200, 200);
		title.setFont(new Font("Serif", Font.BOLD, 22));
		title.setForeground(Color.RED);
		JTextField titleField = new JTextField();
		titleField.setBounds(250, 387, 250, 30);
		
		
		JLabel entry = new JLabel("Entry:");
		entry.setBounds(130, 400, 200, 200);
		entry.setFont(new Font("Serif", Font.BOLD, 22));
		entry.setForeground(Color.RED);
		JTextArea entryField = new JTextArea();
		entryField.setLineWrap(true);
		entryField.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(entryField);
		scrollPane.setBounds(250, 487, 350, 100);
		
		JButton createButton = new JButton("Create");
		createButton.setBounds(170, 620, 100, 30);
		createButton.setBackground(Color.RED);
		createButton.setForeground(Color.DARK_GRAY);
		
		JButton viewButton = new JButton("View Notes");
		viewButton.setBounds(230, 660, 200, 30);
		viewButton.setBackground(Color.RED);
		viewButton.setForeground(Color.WHITE);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(280, 620, 100, 30);
		updateButton.setBackground(Color.RED);
		updateButton.setForeground(Color.DARK_GRAY);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(390, 620, 100, 30);
		deleteButton.setBackground(Color.RED);
		deleteButton.setForeground(Color.DARK_GRAY);
		
		frame.setLayout(null);
		jp.add(label);
		frame.add(subject);
		frame.add(subjectField);
		frame.add(title);
		frame.add(titleField);
		frame.add(entry);
		frame.add(scrollPane);
		frame.add(createButton);
		frame.add(viewButton);
		frame.add(updateButton);
		frame.add(deleteButton);
		
		frame.setSize(700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
// Added Class
class MyJPanel extends JPanel{
	BufferedImage image = null;

	public MyJPanel(){
	    try {
	        image = ImageIO.read(new File("sunset.jpg"));
	    } catch (IOException ex) {
	        System.out.println("Failed to load image");
	        System.exit(1);
	    }
	    setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    //draw image from top left corner
	    g.drawImage(image, 0, 0, this);
	}
}
