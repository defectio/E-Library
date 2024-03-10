package com.defectio.library.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")  
// /admin에 대한 요청을 AdminHomeController에서 처리한다
public class AdminHomeController {

	/**
	 * 
	 * @return
	 */
	/*
	 * value : /admin, /admin/ 에 대한 요청을 home() 에서 처리한다.
	 * method : 요청 정보 전송방식(get, post)
	 */
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public String home() {
		return "admin/home";
	}
	
}
