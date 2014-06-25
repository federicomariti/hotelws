package com.hotel.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	public static Connection getConnection(String server, String database, String user, String passw)
    throws ClassNotFoundException, SQLException {
    	Class.forName("org.postgresql.Driver");
    	Connection conn = DriverManager.getConnection("jdbc:postgresql://" + server + "/" + database, user, passw);
    	if (null == conn) {
    		System.err.println("Connection is null");
    		throw new SQLException("Connection is null");
    	}
    	conn.setAutoCommit(true);
    	return conn;
    }
	
	public static void printCorniceTab(int colNum, int ampiezzaCol) {
		for (int i=0; i<=colNum*ampiezzaCol; i++)
			if (i%ampiezzaCol == 0) System.out.print("|");
			else System.out.print("-");
		System.out.println("");
	}

	public static void printTable(ResultSet resultSet) throws Exception {
		int ampiezzaCol = 19; 
		int colNum = resultSet.getMetaData().getColumnCount();
		printCorniceTab(colNum, ampiezzaCol);
		for (int i=0; i<colNum; i++)
			System.out.printf("| %-16s ", resultSet.getMetaData().getColumnLabel(i+1));
		System.out.println("|");
		printCorniceTab(colNum, ampiezzaCol);
		while (resultSet.next()) {
			for (int i=0; i<colNum; i++)
				System.out.printf("| %-16s ", resultSet.getString(i+1));
			System.out.println("|");
		}
		printCorniceTab(colNum, ampiezzaCol);
	}

}
