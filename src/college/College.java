/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package college;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class College {

	private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=College;encrypt=true;trustServerCertificate=true;";
	private static final String user = "project";
	private static final String pass = "123456";

	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection True");
		} catch (SQLException e) {
			System.out.println("Connection Error");
		}
		return con;
	}

	public static void main(String[] args) {

		Connection mainConnection = College.getConnection();

		Instructor I = new Instructor();
		Department D = new Department();
		Courses C = new Courses();
		Student S = new Student();
		Enrollment E = new Enrollment();
		HomePage H = new HomePage();

		// Initially hide all frames
		I.setVisible(false);
		D.setVisible(false);
		C.setVisible(false);
		S.setVisible(false);
		E.setVisible(false);

		// Pass reference of all frames to each other
		S.setOtherFrames(I, D, C, E, H);
		I.setOtherFrames(S, D, C, E, H);
		D.setOtherFrames(S, I, C, E, H);
		C.setOtherFrames(S, I, D, E, H);
		E.setOtherFrames(S, I, D, C, H);
		H.setOtherFrames(I, D, C, S, E);

		// Set HomePage visible
		H.setVisible(true);

	}
}
