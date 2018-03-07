package com.ef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ef.db.DatabaseHandler;
import com.ef.model.AppArguments;
import com.ef.model.DatabaseConfig;
import com.ef.model.LogEntry;
import com.ef.utils.DatabaseConfigUtils;
import com.ef.utils.ParserUtils;

public class Parser {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		DatabaseConfig databaseConfig = DatabaseConfigUtils.getDatabaseConfig();
		if (databaseConfig != null) {
			AppArguments appArguments = new AppArguments(args);
			if (appArguments.isValidArguments()) {
				DatabaseHandler dbHandler = new DatabaseHandler(databaseConfig);
				if (dbHandler.isDatabaseConnectionValid()) {
					if (appArguments.isDataLoad()) {
						dbHandler.clearLogs();
						dbHandler.clearBlockedInfo();
						try (BufferedReader br = new BufferedReader(
								new FileReader(new File(appArguments.getAccesslog())))) {
							int numberOfLinesRead = 0;
							List<LogEntry> logEntries = new ArrayList<LogEntry>();
							for (String line; (line = br.readLine()) != null;) {
								logEntries.add(ParserUtils.getLogEntry(line));
								if (numberOfLinesRead % databaseConfig.getDbBatchInsertCount() == 0) {
									dbHandler.addLogEntry(logEntries);
									logEntries = new ArrayList<LogEntry>();
								}
								numberOfLinesRead++;
							}
							dbHandler.addLogEntry(logEntries);
						}
					}
					List<String> ipAddressList = dbHandler.getListOfIpAddress(appArguments.getStartDate(),
							appArguments.getDuration(), appArguments.getThreshold());
					if (ipAddressList.size() == 0) {
						System.out.println("No ip addresses found for the search criteria.");
					} else {
						for (String ipAddress : ipAddressList) {
							System.out.println(ipAddress);
						}
					}
				}
			}
		}
	}

}
