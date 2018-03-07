package com.ef.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ef.model.AppConstants;
import com.ef.model.LogEntry;

public class ParserUtils {

	public static LogEntry getLogEntry(String logEntryString) {
		String[] logEntryStringSplit = logEntryString.split(AppConstants.REGEX_DELIMITER_PIPE);
		LogEntry logEntry = new LogEntry();
		try {
			SimpleDateFormat format = new SimpleDateFormat(AppConstants.LOG_ENTRY_DATE);
			Date entryDate = format.parse(logEntryStringSplit[AppConstants.LOG_ENTRY_DATE_INDEX]);
			logEntry.setEntryDate(new java.sql.Date(entryDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logEntry.setIpAddress(logEntryStringSplit[AppConstants.LOG_ENTRY_IPADDRESS_INDEX]);
		String[] requestType = StringUtils.removeQuotes(logEntryStringSplit[AppConstants.LOG_ENTRY_REQUEST_TYPE_INDEX])
				.split(AppConstants.REGEX_DELIMITER_SPACE);
		logEntry.setRequestType(requestType[AppConstants.LOG_ENTRY_REQUEST_TYPE_SUB_INDEX]);
		logEntry.setDocumentURI(requestType[AppConstants.LOG_ENTRY_DOCUMENT_URI_INDEX]);
		logEntry.setProtocol(requestType[AppConstants.LOG_ENTRY_PROTOCOL_INDEX]);
		logEntry.setStatusCode(Integer.parseInt(logEntryStringSplit[AppConstants.LOG_ENTRY_STATUS_CODE_INDEX]));
		logEntry.setUserAgent(StringUtils.removeQuotes(logEntryStringSplit[AppConstants.LOG_ENTRY_USER_AGENT_INDEX]));
		return logEntry;
	}

}
