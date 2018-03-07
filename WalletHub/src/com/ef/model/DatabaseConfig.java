package com.ef.model;

public class DatabaseConfig {
	private String dbURL;
	private String dbUsername;
	private String dbPassword;
	private int dbBatchInsertCount;

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public int getDbBatchInsertCount() {
		return dbBatchInsertCount;
	}

	public void setDbBatchInsertCount(int dbBatchInsertCount) {
		this.dbBatchInsertCount = dbBatchInsertCount;
	}
}
