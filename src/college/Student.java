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

public class Student extends Frame implements ActionListener {

	Instructor I;
	Department D;
	Courses C;
	Enrollment E;
	HomePage H;

    private Image img; 

	public void setOtherFrames(Instructor I, Department D, Courses C, Enrollment E, HomePage H) {

		this.I = I;
		this.D = D;
		this.C = C;
		this.E = E;
		this.H = H;
	}

// ALL Label
	Label idLabel = new Label("Student ID:");
	Label fnameLabel = new Label("First Name:");
	Label lnameLabel = new Label("Last Name:");
	Label fphoneLabel = new Label("First Phone:");
	Label lphoneLabel = new Label("Last Phone:");

//ALL Text
	TextField idField = new TextField();
	TextField fnameField = new TextField();
	TextField lnameField = new TextField();
	TextField fphoneField = new TextField();
	TextField lphoneField = new TextField();

	//ALL Button
	Button insertButton = new Button("Insert");
	Button resetButton = new Button("Reset");
	Button updateButton = new Button("Update");
	Button deleteButton = new Button("Delete");
	Button exitButton = new Button("Back");

//Constructor
	Student() {

		try {
            img = ImageIO.read(new File("C:\\Users\\moham\\OneDrive\\Pictures\\Camera Roll\\Student.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		setLayout(null);
		setBounds(250, 250, 650, 450);
		setTitle("Student Management");

		// Set foreground for labels
		idLabel.setForeground(Color.BLACK);
		fnameLabel.setForeground(Color.BLACK);
		lnameLabel.setForeground(Color.BLACK);
		fphoneLabel.setForeground(Color.BLACK);
		lphoneLabel.setForeground(Color.BLACK);

		// Background
		setBackground(Color.WHITE.darker());

		// Set bounds for labels and fields
		idLabel.setBounds(30, 50, 120, 30);
		idField.setBounds(160, 50, 150, 30);
		fnameLabel.setBounds(30, 100, 120, 30);
		fnameField.setBounds(160, 100, 150, 30);
		lnameLabel.setBounds(30, 150, 120, 30);
		lnameField.setBounds(160, 150, 150, 30);
		fphoneLabel.setBounds(30, 200, 120, 30);
		fphoneField.setBounds(160, 200, 150, 30);
		lphoneLabel.setBounds(30, 250, 120, 30);
		lphoneField.setBounds(160, 250, 150, 30);

		insertButton.setBounds(100, 320, 80, 30);
		resetButton.setBounds(200, 320, 80, 30);
		updateButton.setBounds(300, 320, 80, 30);
		deleteButton.setBounds(400, 320, 80, 30);
		exitButton.setBounds(250, 370, 80, 30);

		// Add components
		add(idLabel);
		add(idField);
		add(fnameLabel);
		add(fnameField);
		add(lnameLabel);
		add(lnameField);
		add(fphoneLabel);
		add(fphoneField);
		add(lphoneLabel);
		add(lphoneField);


		
		
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
				insertButton.setBackground(Color.WHITE); // Reset to default
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose(); 
				System.exit(0); 
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
			insertStudents();
		} else if (e.getSource() == resetButton) {
			idField.setText("");
			fnameField.setText("");
			lnameField.setText("");
			fphoneField.setText("");
			lphoneField.setText("");
		} else if (e.getSource() == exitButton) {
			this.setVisible(false); // Hide Instructor
			H.setVisible(true);
		} else if (e.getSource() == deleteButton) {
			deleteStudent();
		} else if (e.getSource() == updateButton) {
			updateStudent();
		}
	}

	private void insertStudents() {
		try (Connection conn = College.getConnection()) {
			String sql = "INSERT INTO Students (Student_ID ,F_Name ,L_Name, F_Phone ,L_Phone) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			String studentText = idField.getText().trim();
			if (studentText.isEmpty()) {
				stmt.setNull(1, Types.INTEGER);
			} else {
				stmt.setInt(1, Integer.parseInt(studentText));
			}

			stmt.setString(2, fnameField.getText());
			stmt.setString(3, lnameField.getText());
			stmt.setString(4, fphoneField.getText());
			stmt.setString(5, lphoneField.getText());

			stmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Student inserted!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error inserting Student");
			ex.printStackTrace();
		}
	}

	private void updateStudent() {
		try (Connection conn = College.getConnection()) {
			String sql = "UPDATE Students SET F_Name = ?, L_Name = ?, F_Phone = ?, L_Phone = ? WHERE Student_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, fnameField.getText());
			stmt.setString(2, lnameField.getText());
			stmt.setString(3, fphoneField.getText());
			stmt.setString(4, lphoneField.getText());

			String studentText = idField.getText().trim();
			if (studentText.isEmpty()) {
				stmt.setNull(5, Types.INTEGER);
			} else {
				stmt.setInt(5, Integer.parseInt(studentText));
			}

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Student updated!");
			} else {
				JOptionPane.showMessageDialog(this, "Student not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error updating Student");
			ex.printStackTrace();
		}
	}

	private void deleteStudent() {
		try (Connection conn = College.getConnection()) {
			String sql = "DELETE FROM Students WHERE Student_ID = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);

			String studentText = idField.getText().trim();
			if (studentText.isEmpty()) {
				stmt.setNull(1, Types.INTEGER);
			} else {
				stmt.setInt(1, Integer.parseInt(studentText));
			}
			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				JOptionPane.showMessageDialog(this, "Student deleted!");
			} else {
				JOptionPane.showMessageDialog(this, "Student not found!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error deleting Student");
			ex.printStackTrace();
		}

	}
}
 