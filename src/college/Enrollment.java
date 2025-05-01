/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;



public class Enrollment extends Frame implements ActionListener {

	private Image img;

	Instructor I;
	Department D;
	Courses C;
	Student S;
	HomePage H;

	public void setOtherFrames(Student S, Instructor I, Department D, Courses C, HomePage H) {
		this.S = S;
		this.I = I;
		this.D = D;
		this.C = C;
		this.H = H;
	}

	Label studentIdLabel = new Label("Student ID:");
	Label courseIdLabel = new Label("Course ID:");
	Label dateLabel = new Label("Enrollment Date(yyyy-MM-DD):");

	TextField studentIdField = new TextField();
	TextField courseIdField = new TextField();
	TextField dateField = new TextField();

	Button insertButton = new Button("Insert");
	Button resetButton = new Button("Reset");
	Button updateButton = new Button("Update");
	Button deleteButton = new Button("Delete");
	Button exitButton = new Button("Back");

	Enrollment() {

		try {
			img = ImageIO.read(new File("C:\\Users\\moham\\OneDrive\\Pictures\\Camera Roll\\Enrollment.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(null);
		setBounds(200, 200, 600, 400);
		setTitle("Enrollment Management");

		// Set foreground for labels
		studentIdLabel.setForeground(Color.BLACK);
		courseIdLabel.setForeground(Color.BLACK);
		dateLabel.setForeground(Color.BLACK);

		// Background
		setBackground(Color.lightGray.darker());

		// Set bounds for labels and fields
		studentIdLabel.setBounds(30, 50, 120, 30);
		studentIdField.setBounds(160, 50, 150, 30);
		courseIdLabel.setBounds(30, 100, 120, 30);
		courseIdField.setBounds(160, 100, 150, 30);
		dateLabel.setBounds(30, 150, 170, 30);
		dateField.setBounds(200, 150, 150, 30);

		insertButton.setBounds(100, 220, 80, 30);
		resetButton.setBounds(200, 220, 80, 30);
		updateButton.setBounds(300, 220, 80, 30);
		deleteButton.setBounds(400, 220, 80, 30);
		exitButton.setBounds(250, 270, 80, 30);

		// Add components
		add(studentIdLabel);
		add(studentIdField);
		add(courseIdLabel);
		add(courseIdField);
		add(dateLabel);
		add(dateField);
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
				exitButton.setBackground(Color.WHITE);
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
				deleteButton.setBackground(Color.WHITE);
			}
		});

		insertButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				insertButton.setBackground(Color.GREEN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				insertButton.setBackground(Color.WHITE);
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
			insertEnrollment();
		}

		if (e.getSource() == resetButton) {
			studentIdField.setText("");
			courseIdField.setText("");
			dateField.setText("");
		}

		if (e.getSource() == exitButton) {
			this.setVisible(false); // Hide Instructor
			H.setVisible(true);
		}
		if (e.getSource() == deleteButton) {
			deleteEnrollment();
		}

		if (e.getSource() == updateButton) {
			updateEnrollment();
		}
	}

	private void insertEnrollment() {
		try (Connection conn = College.getConnection()) {
			String sql = "INSERT INTO  Enrollment (Student_ID,  C_ID, Enrollment_Date) VALUES (?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);

			String StudentText = studentIdField.getText().trim();
			if (StudentText.isEmpty()) {
				stmt.setNull(1, Types.INTEGER);
			} else {
				stmt.setInt(1, Integer.parseInt(StudentText));
			}

			String courseText = courseIdField.getText().trim();
			if (courseText.isEmpty()) {
				stmt.setNull(2, Types.INTEGER);
			} else {
				stmt.setInt(2, Integer.parseInt(courseText));
			}

			String dateText = dateField.getText().trim();
			if (dateText.isEmpty()) {
				stmt.setNull(3, Types.DATE);
			} else {
				stmt.setDate(3, Date.valueOf(dateText)); // must be yyyy-MM-dd
			}

			stmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Enrollment inserted!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error inserting Enrollment");
			ex.printStackTrace();
		}
	}
// Error

	private void updateEnrollment() {
		try (Connection conn = College.getConnection()) {
			String sql = "UPDATE Enrollment SET Enrollment_Date = ?, C_ID = ? WHERE C_ID = ? AND Student_ID  = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			String dateText = dateField.getText().trim();
			if (dateText.isEmpty()) {
				stmt.setNull(1, Types.DATE);
			} else {
				stmt.setDate(1, Date.valueOf(dateText)); // must be yyyy-MM-dd
			}
			String StudentText = studentIdField.getText().trim();
			if (StudentText.isEmpty()) {
				stmt.setNull(2, Types.INTEGER);
			} else {
				stmt.setInt(2, Integer.parseInt(StudentText));
			}

			String courseText = courseIdField.getText().trim();
			if (courseText.isEmpty()) {
				stmt.setNull(3, Types.INTEGER);
			} else {
				stmt.setInt(3, Integer.parseInt(courseText));
			}

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Enrollment updated!");
			} else {
				JOptionPane.showMessageDialog(this, "Enrollment not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error updating Enrollment");
			ex.printStackTrace();
		}
	}

	private void deleteEnrollment() {
		try (Connection conn = College.getConnection()) {
			String sql = "DELETE FROM Enrollment WHERE C_ID = ? AND Student_ID  = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			String StudentText = studentIdField.getText().trim();
			if (StudentText.isEmpty()) {
				stmt.setNull(1, Types.INTEGER);
			} else {
				stmt.setInt(1, Integer.parseInt(StudentText));
			}

			String courseText = courseIdField.getText().trim();
			if (courseText.isEmpty()) {
				stmt.setNull(2, Types.INTEGER);
			} else {
				stmt.setInt(2, Integer.parseInt(courseText));
			}

			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				JOptionPane.showMessageDialog(this, "Enrollment deleted!");
			} else {
				JOptionPane.showMessageDialog(this, "Enrollment not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error deleting Enrollment");
			ex.printStackTrace();
		}

	}
}
