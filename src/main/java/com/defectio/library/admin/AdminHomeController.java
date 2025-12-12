package com.defectio.library.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller		// 프로젝트가 실행될 때 IoC컨테이너에 bean 객체로 생성된다.
@RequestMapping("/admin")  
// "/admin"에 대한 요청을 AdminController가 처리한다. 
// @RequestMapping("/admin")를 명시하지 않으면 AdminController는 클라이언트의 모든 요청을 처리할 수 있는 컨트롤러가 된다.
public class AdminHomeController {

	/**
	 * value : /admin, /admin/ 에 대한 요청을 home() 에서 처리한다.
	 * method : 요청 정보 전송방식(get, post)
	 * @return
	 */
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public String home() {
		return "admin/home";
	}
	
}
