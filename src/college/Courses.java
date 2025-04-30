/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
/**
 *
 * @author Ziad Salah Mohamed
 */

public class Courses extends Frame implements ActionListener {

	private Image img; 

	Student S;
	Instructor I;
	Department D;
	Enrollment E;
	HomePage H;

	public void setOtherFrames(Student S, Instructor I, Department D, Enrollment E, HomePage H) {
		this.S = S;
		this.I = I;
		this.D = D;
		this.E = E;
		this.H = H;
	}

	Label nameLabel = new Label("Course Name:");
	Label idLabel = new Label("Course ID:");
	Label durationLabel = new Label("Duration (1--3):");
	Label instructorIdLabel = new Label("Instructor ID:");

	TextField nameField = new TextField();
	TextField idField = new TextField();
	TextField durationField = new TextField();
	TextField instructorIdField = new TextField();

	Button insertButton = new Button("Insert");
	Button resetButton = new Button("Reset");
	Button updateButton = new Button("Update");
	Button deleteButton = new Button("Delete");
	Button exitButton = new Button("Back");

	Courses() {
		
		try {
            img = ImageIO.read(new File("C:\\Users\\moham\\OneDrive\\Pictures\\Camera Roll\\Courses.jpg")); 
        } catch (Exception e) {
            e.printStackTrace();
        }
		setLayout(null);
		setBounds(200, 200, 600, 400);
		setTitle("Courses Management");

		// Set foreground for labels
		nameLabel.setForeground(Color.BLACK);
		idLabel.setForeground(Color.BLACK);
		durationLabel.setForeground(Color.BLACK);
		instructorIdLabel.setForeground(Color.BLACK);

		// Background
		setBackground(Color.lightGray.darker());

		// Set bounds for labels and fields
		nameLabel.setBounds(30, 50, 120, 30);
		nameField.setBounds(160, 50, 150, 30);
		idLabel.setBounds(30, 100, 120, 30);
		idField.setBounds(160, 100, 150, 30);
		durationLabel.setBounds(30, 150, 120, 30);
		durationField.setBounds(160, 150, 150, 30);
		instructorIdLabel.setBounds(30, 200, 120, 30);
		instructorIdField.setBounds(160, 200, 150, 30);

		insertButton.setBounds(100, 270, 80, 30);
		resetButton.setBounds(200, 270, 80, 30);
		updateButton.setBounds(300, 270, 80, 30);
		deleteButton.setBounds(400, 270, 80, 30);
		exitButton.setBounds(250, 320, 80, 30);

		// Add components
		add(nameLabel);
		add(nameField);
		add(idLabel);
		add(idField);
		add(durationLabel);
		add(durationField);
		add(instructorIdLabel);
		add(instructorIdField);
		add(insertButton);
		add(resetButton);
		add(updateButton);
		add(deleteButton);
		add(exitButton);

		// Add action listeners
		insertButton.addActionListener(this);
		resetButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		exitButton.addActionListener(this);

// Add mouse hover effect exitButton 
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setBackground(Color.WHITE); // Reset to default
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		// Add mouse hover effect deleteButton
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteButton.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				deleteButton.setBackground(Color.WHITE); // Reset to default
			}
		});

		insertButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				insertButton.setBackground(Color.GREEN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				insertButton.setBackground(Color.WHITE); // Reset to default
			}
		});
		setVisible(false);
	}
public void paint(Graphics g) {
    if (img != null) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this); 
    }
}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insertButton) {
			insertCourese();
		}

		if (e.getSource() == resetButton) {
			nameField.setText("");
			idField.setText("");
			durationField.setText("");
			instructorIdField.setText("");

		}

		if (e.getSource() == exitButton) {
			this.setVisible(false); // Hide Instructor
			H.setVisible(true);
		}
		if (e.getSource() == deleteButton) {
			deleteCourses();
		}
		if (e.getSource() == updateButton) {
			updateCourses();
		}

	}

	private void insertCourese() {
		try (Connection conn = College.getConnection()) {
			String sql = "INSERT INTO Courses (C_Name ,C_ID ,Duration ,Instructor_ID) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nameField.getText());

			String coursesText = idField.getText().trim();
			if (coursesText.isEmpty()) {
				stmt.setNull(2, Types.INTEGER);
			} else {
				stmt.setInt(2, Integer.parseInt(coursesText));
			}

			String durationText = durationField.getText().trim();
			if (durationText.isEmpty()) {
				stmt.setNull(3, Types.INTEGER);
			} else {
				stmt.setInt(3, Integer.parseInt(durationText));
			}

			String instructorText = instructorIdField.getText().trim();
			if (instructorText.isEmpty()) {
				stmt.setNull(4, Types.INTEGER);
			} else {
				stmt.setInt(4, Integer.parseInt(instructorText));
			}

			stmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Courses inserted!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error inserting Courses");
			ex.printStackTrace();
		}
	}

	private void updateCourses() {
		try (Connection conn = College.getConnection()) {
			String sql = "UPDATE Courses SET C_Name = ?, Duration = ?,Instructor_ID = ? WHERE C_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nameField.getText());

			String durationText = durationField.getText().trim();
			if (durationText.isEmpty()) {
				stmt.setNull(2, Types.INTEGER);
			} else {
				stmt.setInt(2, Integer.parseInt(durationText));
			}

			String instructorText = instructorIdField.getText().trim();
			if (instructorText.isEmpty()) {
				stmt.setNull(3, Types.INTEGER);
			} else {
				stmt.setInt(3, Integer.parseInt(instructorText));
			}
			String CoursesText = idField.getText().trim();
			if (CoursesText.isEmpty()) {
				stmt.setNull(4, Types.INTEGER);
			} else {
				stmt.setInt(4, Integer.parseInt(CoursesText));
			}

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Courses updated!");
			} else {
				JOptionPane.showMessageDialog(this, "Courses not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error updating Courses");
			ex.printStackTrace();
		}
	}

	private void deleteCourses() {
		try (Connection conn = College.getConnection()) {
			String sql = "DELETE FROM Courses WHERE C_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			String CoursesText = idField.getText().trim();
			if (CoursesText.isEmpty()) {
				stmt.setNull(1, Types.INTEGER);
			} else {
				stmt.setInt(1, Integer.parseInt(CoursesText));
			}

			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				JOptionPane.showMessageDialog(this, "Courses deleted!");
			} else {
				JOptionPane.showMessageDialog(this, "Courses not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error deleting Courses");
			ex.printStackTrace();
		}

	}
}
