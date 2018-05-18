package com.sunyard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.sunyard.service.HqlOperate;

@Controller
public class BaseController {

	protected Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected HqlOperate hqlOperate;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	
}
