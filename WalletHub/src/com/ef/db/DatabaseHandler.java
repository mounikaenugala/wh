package com.ef.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ef.model.AppConstants;
import com.ef.model.DatabaseConfig;
import com.ef.model.LogEntry;

public class DatabaseHandler {
	Connection con;
	private boolean isDatabaseConnectionValid = false;

	public DatabaseHandler(DatabaseConfig databaseConfig) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(databaseConfig.getDbURL(), databaseConfig.getDbUsername(),
					databaseConfig.getDbPassword());
			if (checkForTables()) {
				setDatabaseConnectionValid(true);
			}
		} catch (Exception e) {
			System.out.println(
					"Error: Couldn't connect to the database. Please check database name, username, password and connection.");
		}
	}

	private boolean checkForTables() {
		return isTableExists(AppConstants.TABLE_LOG_ENTRY) && isTableExists(AppConstants.TABLE_BLOCKED_IPS);
	}

	private boolean isTableExists(String tableName) {
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM " + tableName + " LIMIT 1");
			stmt.executeQuery();
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println(
					"Error: Table " + tableName + " doesn't exist. Please use schema.sql file to create tables.");
		}
		return false;
	}

	public void addLogEntry(List<LogEntry> logEntries) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO " + AppConstants.TABLE_LOG_ENTRY + " ("
					+ AppConstants.COLUMN_LOG_ENTRY_DATE + ", " + AppConstants.COLUMN_LOG_IP_ADDRESS + ", "
					+ AppConstants.COLUMN_LOG_REQUEST_TYPE + ", " + AppConstants.COLUMN_LOG_DOCUMENT_URI + ", "
					+ AppConstants.COLUMN_LOG_PROTOCOL + ", " + AppConstants.COLUMN_LOG_STATUS_CODE + ", "
					+ AppConstants.COLUMN_LOG_USER_AGENT + ")" + "VALUES(?, ?, ?, ?, ?, ?, ?)");
			for (LogEntry logEntry : logEntries) {
				stmt.setTimestamp(1, new Timestamp(logEntry.getEntryDate().getTime()));
				stmt.setString(2, logEntry.getIpAddress());
				stmt.setString(3, logEntry.getRequestType());
				stmt.setString(4, logEntry.getDocumentURI());
				stmt.setString(5, logEntry.getProtocol());
				stmt.setInt(6, logEntry.getStatusCode());
				stmt.setString(7, logEntry.getUserAgent());
				stmt.addBatch();
			}
			stmt.executeBatch();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getListOfIpAddress(java.sql.Date startDate, String duration, int threshold) {
		PreparedStatement stmt = null;
		List<String> ipAddressList = new ArrayList<String>();
		long seconds = 0;
		if (duration.equals(AppConstants.ARG_DURATION_VALUE_HOURLY)) {
			seconds = 3599;// 59 minutes 59 seconds
		} else if (duration.equals(AppConstants.ARG_DURATION_VALUE_DAILY)) {
			seconds = 86399; // 23 hours 59 minutes 59 seconds
		}
		Date endDate = new Date(startDate.getTime() + TimeUnit.SECONDS.toMillis(seconds));
		String startDateStr = new SimpleDateFormat(AppConstants.DB_DATE_FORMAT).format(startDate);
		String endDateStr = new SimpleDateFormat(AppConstants.DB_DATE_FORMAT).format(endDate);
		try {
			stmt = con.prepareStatement("Select " + AppConstants.COLUMN_LOG_IP_ADDRESS + " from (SELECT "
					+ AppConstants.COLUMN_LOG_IP_ADDRESS + ", count(" + AppConstants.COLUMN_LOG_IP_ADDRESS
					+ ") count FROM " + AppConstants.TABLE_LOG_ENTRY + " where (" + AppConstants.COLUMN_LOG_ENTRY_DATE
					+ " between ? and ?) group by " + AppConstants.COLUMN_LOG_IP_ADDRESS
					+ ") ip_stats where count > ?");
			stmt.setString(1, startDateStr);
			stmt.setString(2, new SimpleDateFormat(AppConstants.DB_DATE_FORMAT).format(endDate));
			stmt.setInt(3, threshold);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				ipAddressList.add(result.getString(AppConstants.COLUMN_LOG_IP_ADDRESS));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		addToBlockedIpAddress(ipAddressList, threshold, startDateStr, endDateStr);
		return ipAddressList;
	}

	private void addToBlockedIpAddress(List<String> ipAddressList, int threshold, String startDateStr,
			String endDateStr) {
		try {
			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO " + AppConstants.TABLE_BLOCKED_IPS + " (" + AppConstants.COLUMN_BLOCKED_IP_ADDRESS
							+ ", " + AppConstants.COLUMN_BLOCKED_REASON + ")" + "VALUES(?, ?)");
			for (String ipAddress : ipAddressList) {
				stmt.setString(1, ipAddress);
				stmt.setString(2, ipAddress + " has " + threshold + " or more requests between " + startDateStr
						+ " and " + endDateStr);
				stmt.addBatch();
			}
			stmt.executeBatch();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearLogs() {
		try {
			PreparedStatement preparedStmt = con.prepareStatement("DELETE FROM " + AppConstants.TABLE_LOG_ENTRY);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearBlockedInfo() {
		try {
			PreparedStatement preparedStmt = con.prepareStatement("DELETE FROM " + AppConstants.TABLE_BLOCKED_IPS);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isDatabaseConnectionValid() {
		return isDatabaseConnectionValid;
	}

	private void setDatabaseConnectionValid(boolean isDatabaseConnectionValid) {
		this.isDatabaseConnectionValid = isDatabaseConnectionValid;
	}
}
