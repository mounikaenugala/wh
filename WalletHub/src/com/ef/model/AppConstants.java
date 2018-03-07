package com.ef.model;

public interface AppConstants {

	public static final String DB_CONFIG = "db.config";
	public static final String DATA_FILE = "data/access.log";
	public static final String TEST_DATA_FILE = "data/test_data_access.log";
	public static final String PROPERTY_KEY_DB_BATCH_INSERT_COUNT = "dbbatchinsertcount";
	public static final String LOG_ENTRY_DATE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String REGEX_DELIMITER_PIPE = "\\|";
	public static final String REGEX_DELIMITER_SPACE = "\\s+";
	public static final String DB_URL = "dburl";
	public static final String DB_USERNAME = "dbusername";
	public static final String DB_PASSWORD = "dbpassword";
	public static final String REGEX_QUOTES_REPLACE = "^\"|\"$";
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String TABLE_LOG_ENTRY = "log_entry";
	public static final String COLUMN_LOG_ENTRY_DATE = "log_entry_date";
	public static final String COLUMN_LOG_IP_ADDRESS = "ip_address";
	public static final String COLUMN_LOG_REQUEST_TYPE = "request_type";
	public static final String COLUMN_LOG_DOCUMENT_URI = "document_uri";
	public static final String COLUMN_LOG_PROTOCOL = "protocol";
	public static final String COLUMN_LOG_STATUS_CODE = "status_code";
	public static final String COLUMN_LOG_USER_AGENT = "user_agent";

	public static final String TABLE_BLOCKED_IPS = "blocked_ips";
	public static final String COLUMN_BLOCKED_REASON = "reason";
	public static final String COLUMN_BLOCKED_IP_ADDRESS = "ip_address";

	public static final int LOG_ENTRY_DATE_INDEX = 0;
	public static final int LOG_ENTRY_IPADDRESS_INDEX = 1;
	public static final int LOG_ENTRY_REQUEST_TYPE_INDEX = 2;
	public static final int LOG_ENTRY_REQUEST_TYPE_SUB_INDEX = 0;
	public static final int LOG_ENTRY_DOCUMENT_URI_INDEX = 1;
	public static final int LOG_ENTRY_PROTOCOL_INDEX = 2;
	public static final int LOG_ENTRY_STATUS_CODE_INDEX = 3;
	public static final int LOG_ENTRY_USER_AGENT_INDEX = 4;

	public static final String ARG_START_DATE = "startDate";
	public static final String ARG_DURATION = "duration";
	public static final String ARG_DURATION_VALUE_HOURLY = "hourly";
	public static final String ARG_DURATION_VALUE_DAILY = "daily";
	public static final String ARG_THRESHOLD = "threshold";
	public static final String ARG_LOG_FILE = "accesslog";
	public static final String ARG_START_DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";

}
