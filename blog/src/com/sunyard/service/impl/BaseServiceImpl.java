package com.sunyard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sunyard.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService{

	@Autowired
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	
	public void show(){
		System.out.println("test");
	}
	
	
}
