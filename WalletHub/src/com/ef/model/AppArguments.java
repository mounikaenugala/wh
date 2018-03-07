package com.ef.model;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppArguments {
	private String accesslog;
	private Date startDate;
	private String duration;
	private int threshold;
	private boolean isValidArguments = true;

	public AppArguments(String[] args) {
		for (String str : args) {
			String[] keyValue = str.split("=");
			if (keyValue[0].startsWith("--" + AppConstants.ARG_START_DATE)) {
				try {
					SimpleDateFormat format = new SimpleDateFormat(AppConstants.ARG_START_DATE_FORMAT);
					startDate = new java.sql.Date(format.parse(keyValue[1]).getTime());
				} catch (ParseException e) {
					System.out.println("Error: Invalid Start Date.");
				}
			} else if (keyValue[0].startsWith("--" + AppConstants.ARG_DURATION)) {
				if (keyValue[1].equals(AppConstants.ARG_DURATION_VALUE_DAILY)
						|| keyValue[1].equals(AppConstants.ARG_DURATION_VALUE_HOURLY)) {
					duration = keyValue[1];
				} else {
					System.out.println("Error: Invalid duration value.");
				}
			} else if (keyValue[0].startsWith("--" + AppConstants.ARG_THRESHOLD)) {
				try {
					threshold = Integer.parseInt(keyValue[1]);
				} catch (NumberFormatException nfe) {
					System.out.println("Error: Invalid Threshold value.");
					setValidArguments(false);
				}
			} else if (keyValue[0].startsWith("--" + AppConstants.ARG_LOG_FILE)) {
				setAccesslog(keyValue[1]);
			}
		}
		if (startDate == null) {
			setValidArguments(false);
		}
		if (duration == null) {
			setValidArguments(false);
		}
		if (threshold <= 0) {
			setValidArguments(false);
		}
		if (accesslog != null) {
			File datbaseConfigFile = new File(accesslog);
			if (!datbaseConfigFile.exists()) {
				setValidArguments(false);
				System.out.println("Error: Access Log doesn't exist.");
			}
		}
		if (!isValidArguments) {
			System.out.println(
					"Usage: java -cp \"parser.jar\" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250");
			System.out.println("Or");
			System.out.println(
					"Usage: java -cp \"parser.jar\" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100");
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public boolean isDataLoad() {
		if (accesslog != null) {
			File datbaseConfigFile = new File(accesslog);
			if (datbaseConfigFile.exists()) {
				return true;
			}
		}
		return false;
	}

	public String getAccesslog() {
		return accesslog;
	}

	public void setAccesslog(String accesslog) {
		this.accesslog = accesslog;
	}

	public boolean isValidArguments() {
		return isValidArguments;
	}

	public void setValidArguments(boolean isValidArguments) {
		this.isValidArguments = isValidArguments;
	}
}
