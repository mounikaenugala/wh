package com.ef.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ef.model.AppConstants;
import com.ef.model.DatabaseConfig;

public class DatabaseConfigUtils {
	public static String getProperty(String propertyName) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(AppConstants.DB_CONFIG);
			prop.load(input);
			return prop.getProperty(propertyName);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static int getIntProperty(String propertyName) {
		return Integer.parseInt(getProperty(propertyName));
	}

	public static DatabaseConfig getDatabaseConfig() {
		File datbaseConfigFile = new File(AppConstants.DB_CONFIG);
		if (datbaseConfigFile.exists()) {
			DatabaseConfig databaseConfig = new DatabaseConfig();
			databaseConfig.setDbURL(getProperty(AppConstants.DB_URL));
			databaseConfig.setDbUsername(getProperty(AppConstants.DB_USERNAME));
			databaseConfig.setDbPassword(getProperty(AppConstants.DB_PASSWORD));
			databaseConfig.setDbBatchInsertCount(getIntProperty(AppConstants.PROPERTY_KEY_DB_BATCH_INSERT_COUNT));
			if (databaseConfig.getDbURL() == null) {
				System.out.println("Error: Database URL is not valid.");
			}
			if (databaseConfig.getDbUsername() == null) {
				System.out.println("Error: Database Username is not valid.");
			}
			if (databaseConfig.getDbPassword() == null) {
				System.out.println("Error: Database Password is not valid.");
			}
			return databaseConfig;
		} else {
			System.out.println("Error: Database configuration file " + AppConstants.DB_CONFIG
					+ " not found. Created a empty db.config file please update and continue to store database logs.");
			writeDatabaseConfigFile();
		}
		return null;
	}

	private static void writeDatabaseConfigFile() {
		try {
			FileWriter fw = new FileWriter(AppConstants.DB_CONFIG);
			fw.write(AppConstants.DB_URL + "=" + "jdbc:mysql://localhost:3306/application_logs" + "\n");
			fw.write(AppConstants.DB_USERNAME + "=" + "username" + "\n");
			fw.write(AppConstants.DB_PASSWORD + "=" + "password" + "\n");
			fw.write(AppConstants.PROPERTY_KEY_DB_BATCH_INSERT_COUNT + "=" + "1000" + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
