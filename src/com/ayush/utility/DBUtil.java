package com.ayush.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DBUtil {
	private static Connection conn;

	public DBUtil() {
	}

	public static Connection provideConnection() {
	    try {
	        if (conn == null || conn.isClosed()) {
	            System.out.println("Attempting to read DB connection properties...");

	            ResourceBundle rb;
	            try {
	                rb = ResourceBundle.getBundle("application");
	            } catch (MissingResourceException e) {
	                System.err.println("❌ Could not find 'application.properties'. Make sure it's in the classpath.");
	                e.printStackTrace();
	                return null;
	            }

	            String connectionString = rb.getString("db.connectionString");
	            String driverName = rb.getString("db.driverName");
	            String username = rb.getString("db.username");
	            String password = rb.getString("db.password");

	            System.out.println("DB URL: " + connectionString);
	            System.out.println("DB Driver: " + driverName);
	            System.out.println("DB Username: " + username);

	            try {
	                Class.forName(driverName);
	            } catch (ClassNotFoundException e) {
	                System.err.println("❌ JDBC Driver not found: " + driverName);
	                e.printStackTrace();
	                return null;
	            }

	            try {
	                conn = DriverManager.getConnection(connectionString, username, password);
	                System.out.println("✅ Database connection established.");
	            } catch (SQLException e) {
	                System.err.println("❌ Could not connect to the database with the provided credentials.");
	                e.printStackTrace();
	                return null;
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("❌ SQL exception occurred while checking connection state.");
	        e.printStackTrace();
	        return null;
	    }
		return conn;
	}

	public static void closeConnection(Connection con) {
		/*
		 * try { if (con != null && !con.isClosed()) {
		 * 
		 * con.close(); } } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	public static void closeConnection(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeConnection(PreparedStatement ps) {
		try {
			if (ps != null && !ps.isClosed()) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
