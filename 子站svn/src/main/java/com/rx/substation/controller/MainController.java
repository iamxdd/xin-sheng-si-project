package com.rx.substation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {
	@RequestMapping(value = "home")
	public String home(){
		return "index";
	}
}
