package simpleNotes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SimpleNotes {
	public static void Window() {
		
		JFrame frame = new JFrame("Simple Notes - A Note Taking Application");
		ImageIcon background_image = new ImageIcon("sunset.jpg");
		JLabel background = new JLabel("", background_image, JLabel.CENTER);
		background.setBounds(0, 0, 700, 800);
		
		JPanel heading = new JPanel();
		heading.setBackground(new Color(0,0,0,80));
		heading.setBounds(0, 0, 700, 80);
		
		JPanel textA = new JPanel();
		textA.setBackground(new Color(0,0,0,80));
		textA.setBounds(120, 280, 500, 330);
		
		JLabel label = new JLabel("Notes");
		label.setBounds(300, 50, 200, 20);
		label.setFont(new Font("Monospaced", Font.BOLD, 30));
		label.setForeground(Color.RED);
		
		JLabel subject = new JLabel("Subject:");
		subject.setBounds(130, 200, 200, 200);
		subject.setFont(new Font("Serif", Font.BOLD, 22));
		subject.setForeground(Color.WHITE);
		JTextField subjectField = new JTextField();
		subjectField.setBounds(250, 287, 250, 30);
		
		JLabel title = new JLabel("Title:");
		title.setBounds(130, 300, 200, 200);
		title.setFont(new Font("Serif", Font.BOLD, 22));
		title.setForeground(Color.WHITE);
		JTextField titleField = new JTextField();
		titleField.setBounds(250, 387, 250, 30);
		
		JLabel entry = new JLabel("Entry:");
		entry.setBounds(130, 400, 200, 200);
		entry.setFont(new Font("Serif", Font.BOLD, 22));
		entry.setForeground(Color.WHITE);
		JTextArea entryField = new JTextArea();
		entryField.setLineWrap(true);
		entryField.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(entryField);
		scrollPane.setBounds(250, 487, 350, 100);
		
		JButton createButton = new JButton("Create");
		createButton.setBounds(170, 620, 100, 30);
		createButton.setBackground(Color.RED);
		createButton.setForeground(Color.BLACK);
		
		JButton viewButton = new JButton("View Note");
		viewButton.setBounds(230, 660, 200, 30);
		viewButton.setBackground(Color.RED);
		viewButton.setForeground(Color.WHITE);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(280, 620, 100, 30);
		updateButton.setBackground(Color.RED);
		updateButton.setForeground(Color.BLACK);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(390, 620, 100, 30);
		deleteButton.setBackground(Color.RED);
		deleteButton.setForeground(Color.BLACK);
		
		frame.setLayout(null);
		frame.add(heading);
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
		frame.add(textA);
		frame.add(background);
		heading.add(label);
		
		frame.getContentPane().setBackground(Color.DARK_GRAY);;
		frame.setSize(700, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		HandleEvent handleEvent = new HandleEvent(frame, subjectField, titleField, entryField,
				createButton, viewButton, updateButton, deleteButton);
		
		createButton.addActionListener(handleEvent);
		viewButton.addActionListener(handleEvent);
		updateButton.addActionListener(handleEvent);
		deleteButton.addActionListener(handleEvent);
		
	}
	
	
	public static void main(String[] args) {
		Window();
	}
	
	
	static class HandleEvent implements ActionListener {
		
		JTextField subject, title;
		JTextArea entry;
		JButton create, view, update, delete;
		JFrame frame;
		public static Connection con;
		public static PreparedStatement statement;
		
		public HandleEvent(JFrame frame, JTextField subject, JTextField title, JTextArea entry,
				JButton create, JButton view, JButton update, JButton delete) {
			this.frame = frame;
			this.subject = subject;
			this.title = title;
			this.entry = entry;
			this.create = create;
			this.view = view;
			this.update = update;
			this.delete = delete;
			
		}
		
		
		@Override
		public void actionPerformed(ActionEvent a) {
			
			if(a.getSource() == create) {
				connect();
				String noteSubject = subject.getText();
				String noteTitle = title.getText();
				String noteEntry = entry.getText();
				
				try {
					statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS notes(noteSubject VARCHAR PRIMARY KEY,"
							+ " noteTitle VARCHAR NOT NULL, noteEntry VARCHAR NOT NULL);");
					statement.execute();
					statement = con.prepareStatement("insert into notes(noteSubject,"
							+ " noteTitle, noteEntry)values(?,?,?);");
					statement.setString(1, noteSubject);
					statement.setString(2, noteTitle);
					statement.setString(3, noteEntry);
					statement.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Note Created Successfully!", "Note Created",
							JOptionPane.INFORMATION_MESSAGE);
					
					subject.setText("");
					title.setText("");
					entry.setText("");
					
					con.close();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			
			if(a.getSource() == view) {
				connect();
				String noteTitle = title.getText();
				
				try {
					statement = con.prepareStatement("select noteSubject, noteTitle, noteEntry from notes where "
							+ "noteTitle = ?");
					statement.setString(1, noteTitle);
					ResultSet rs = statement.executeQuery();
					
					if (rs.next() == true) {
						String noteSubject = rs.getString(1);
						noteTitle = rs.getString(2);
						String noteEntry = rs.getString(3);
						
						JDialog dialog = new JDialog(frame, "Note");
						dialog.setSize(500, 600);
						dialog.getContentPane().setBackground(Color.DARK_GRAY);
						dialog.setVisible(true);
						
						JLabel l0 = new JLabel("Subject:          "+ noteSubject);
						l0.setBounds(150, 70, 200, 100);
						l0.setForeground(Color.RED);
						
						JLabel l1 = new JLabel("Title:          " + noteTitle);
						l1.setBounds(150, 90, 200, 250);
						l1.setForeground(Color.RED);
						
						JLabel l2 = new JLabel("Entry:          " + noteEntry);
						l2.setBounds(150, 150, 300, 400);
						l2.setForeground(Color.RED);
						
						dialog.add(l0);
						dialog.add(l1);
						dialog.add(l2);
						dialog.setDefaultCloseOperation(1);
						
						con.close();
						
					} else {
						subject.setText("");
						title.setText("");
						entry.setText("");
						
						JOptionPane.showMessageDialog(null, "Please Enter A Valid Title", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			
			if(a.getSource() == update) {
				connect();
				String noteSubject = subject.getText();
				String noteTitle = title.getText();
				String noteEntry = entry.getText();
				
				try {
					statement = con.prepareStatement("update notes set noteSubject = ?, noteTitle = ?,"
							+ " noteEntry = ? where noteTitle = ?");
					statement.setString(1, noteSubject);
					statement.setString(2, noteTitle);
					statement.setString(3, noteEntry);
					statement.setString(4, noteTitle);
					statement.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Note Has Been Updated!", "Note Updated",
							JOptionPane.INFORMATION_MESSAGE);
					
					subject.setText("");
					title.setText("");
					entry.setText("");
					
					con.close();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			
			if(a.getSource() == delete) {
				connect();
				String noteTitle = title.getText();
				
				try {
					statement = con.prepareStatement("delete from notes where notetitle = ?");
					statement.setString(1, noteTitle);
					statement.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Note Has Been Deleted!", "Note Deleted",
							JOptionPane.INFORMATION_MESSAGE);
					
					subject.setText("");
					title.setText("");
					entry.setText("");
					
					con.close();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			
		}
		
		public static void connect() {
			String URL = "jdbc:sqlite:notes.db";
			
			try {
				con = DriverManager.getConnection(URL);
				System.out.println("Successfully Connected to Database...");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
}

