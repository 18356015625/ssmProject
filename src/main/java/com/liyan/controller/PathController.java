package com.liyan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {

	@RequestMapping("learn")
	public String test(){
		return "learn";
	}
	
	@RequestMapping("test")
	public String test1(){
		return "test";
	}
	
	@RequestMapping("table")
	public String table(){
		return "table";
	}
	
	@RequestMapping("user")
	public String user() {
		return "user";
	}
	
	@RequestMapping("calendar")
	public String calendar() {
		return "fullcalendar";
	}
}
