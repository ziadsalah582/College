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


public class HomePage extends Frame implements ActionListener {

	
		private Image img; 

	Button instructorButton = new Button("Instructor");
	Button departmentButton = new Button("Department");
	Button coursesButton = new Button("Courses");
	Button studentButton = new Button("Student");
	Button enrollmentButton = new Button("Enrollment");

	Instructor I;
	Department D;
	Courses C;
	Student S;
	Enrollment E;

	public void setOtherFrames(Instructor I, Department D, Courses C, Student S, Enrollment E) {

		this.I = I;
		this.D = D;
		this.C = C;
		this.S = S;
		this.E = E;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	public HomePage() {
		try {
            img = ImageIO.read(new File("C:\\Users\\moham\\OneDrive\\Pictures\\Camera Roll\\College.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		setLayout(null);
		setBounds(300, 200, 500, 400);
		setTitle("Home Page");
		setBackground(Color.WHITE);

		instructorButton.setBounds(150, 50, 200, 40);
		departmentButton.setBounds(150, 100, 200, 40);
		coursesButton.setBounds(150, 150, 200, 40);
		studentButton.setBounds(150, 200, 200, 40);
		enrollmentButton.setBounds(150, 250, 200, 40);

		add(instructorButton);
		add(departmentButton);
		add(coursesButton);
		add(studentButton);
		add(enrollmentButton);

		instructorButton.addActionListener(this);
		departmentButton.addActionListener(this);
		coursesButton.addActionListener(this);
		studentButton.addActionListener(this);
		enrollmentButton.addActionListener(this);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false); // Hide home page

		if (e.getSource() == instructorButton) {
			I.setVisible(true);
		} else if (e.getSource() == departmentButton) {
			D.setVisible(true);
		} else if (e.getSource() == coursesButton) {
			C.setVisible(true);
		} else if (e.getSource() == studentButton) {
			S.setVisible(true);
		} else if (e.getSource() == enrollmentButton) {
			E.setVisible(true);
		}
	}
public void paint(Graphics g) {
    if (img != null) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this); 
    }
}
}
