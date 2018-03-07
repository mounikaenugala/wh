delimiter $$

CREATE TABLE `blocked_ips` (
  `ip_address` varchar(45) NOT NULL,
  `reason` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `log_entry` (
  `log_entry_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ip_address` varchar(45) NOT NULL,
  `request_type` varchar(6) NOT NULL,
  `document_uri` text NOT NULL,
  `protocol` varchar(10) NOT NULL,
  `status_code` int(11) NOT NULL,
  `user_agent` text NOT NULL,
  KEY `ip_address_index` (`ip_address`) USING HASH,
  KEY `log_entry_date` (`log_entry_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

