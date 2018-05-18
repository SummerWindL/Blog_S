package com.sunyard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseServiceImportent {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
}
