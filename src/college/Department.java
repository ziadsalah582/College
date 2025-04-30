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

public class Department extends Frame implements ActionListener {

private Image img; 

	
	Student S;
	Instructor I;
	Courses C;
	Enrollment E;
	HomePage H;

	public void setOtherFrames(Student S, Instructor I, Courses C, Enrollment E, HomePage H) {
		this.S = S;
		this.I = I;
		this.C = C;
		this.E = E;
		this.H = H;
	}
	Label nameLabel = new Label("Department Name:");
	Label locationLabel = new Label("Location:");
	Label instructorIdLabel = new Label("Instructor ID:");

	TextField nameField = new TextField();
	TextField locationField = new TextField();
	TextField instructorIdField = new TextField();

	Button insertButton = new Button("Insert");
	Button resetButton = new Button("Reset");
	Button updateButton = new Button("Update");
	Button deleteButton = new Button("Delete");
	Button exitButton = new Button("Back");

	Department() {
		
		try {
            img = ImageIO.read(new File("C:\\Users\\moham\\OneDrive\\Pictures\\Camera Roll\\Department.jpg")); 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		setLayout(null);
		setBounds(200, 200, 600, 400);
		setTitle("Department Management");

		// Set foreground for labels
		nameLabel.setForeground(Color.BLACK);
		locationLabel.setForeground(Color.BLACK);
		instructorIdLabel.setForeground(Color.BLACK);

		// Background
		setBackground(Color.gray.darker());

		// Set bounds for labels and fields
		nameLabel.setBounds(30, 50, 120, 30);
		nameField.setBounds(160, 50, 150, 30);
		locationLabel.setBounds(30, 100, 120, 30);
		locationField.setBounds(160, 100, 150, 30);
		instructorIdLabel.setBounds(30, 150, 120, 30);
		instructorIdField.setBounds(160, 150, 150, 30);

		insertButton.setBounds(100, 220, 80, 30);
		resetButton.setBounds(200, 220, 80, 30);
		updateButton.setBounds(300, 220, 80, 30);
		deleteButton.setBounds(400, 220, 80, 30);
		exitButton.setBounds(250, 270, 80, 30);

		// Add components
		add(nameLabel);
		add(nameField);
		add(locationLabel);
		add(locationField);
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
			insertDepartment();
		}

		if (e.getSource() == resetButton) {
			nameField.setText("");
			locationField.setText("");
			instructorIdField.setText("");
		}

		if (e.getSource() == exitButton) {
			this.setVisible(false); // Hide Department
			H.setVisible(true);
		}
		if (e.getSource() == deleteButton) {
			deleteDepartment();
		}

		if (e.getSource() == updateButton) {
			updateDepartment();
		}
	}

	private void insertDepartment() {
		try (Connection conn = College.getConnection()) {

			String sql = "INSERT INTO Department (D_Name, D_Location, Instructor_ID) VALUES (?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nameField.getText());
			stmt.setString(2, locationField.getText());

			String instructorText = instructorIdField.getText().trim();
			if (instructorText.isEmpty()) {
				stmt.setNull(3, Types.INTEGER);
			} else {
				stmt.setInt(3, Integer.parseInt(instructorText));
			}

			stmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Department inserted!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error inserting Department");
			ex.printStackTrace();
		}
	}

	private void updateDepartment() {
		try (Connection conn = College.getConnection()) {
			String sql = "UPDATE Department SET D_Location = ?,Instructor_ID = ?  WHERE D_Name = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, locationField.getText());

			String instructorText = instructorIdField.getText().trim();
			if (instructorText.isEmpty()) {
				stmt.setNull(2, Types.INTEGER);
			} else {
				stmt.setInt(2, Integer.parseInt(instructorText));
			}

			stmt.setString(3, nameField.getText());

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Department updated!");
			} else {
				JOptionPane.showMessageDialog(this, "Department not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error updating Department");
			ex.printStackTrace();
		}
	}

	private void deleteDepartment() {
		try (Connection conn = College.getConnection()) {
			String sql = "DELETE FROM Department WHERE D_Name = ? AND D_Location = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nameField.getText().trim());
			stmt.setString(2, locationField.getText().trim());

			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				JOptionPane.showMessageDialog(this, "Department deleted!");
			} else {
				JOptionPane.showMessageDialog(this, "Department not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error deleting Department");
			ex.printStackTrace();
		}

	}
}
