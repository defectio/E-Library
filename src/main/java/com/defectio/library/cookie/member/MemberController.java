package com.defectio.library.cookie.member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping({"", "/"})
	public String home() {
		String nextPage = "member/cookie/home";
		return nextPage;
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "member/cookie/login_form";
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(@RequestParam("m_id") String m_id, @RequestParam("m_pw") String m_pw,
			HttpServletResponse response) {
		
		String nextPage = "member/cookies/login_ok";
		
		if (m_id.equals("user") && m_pw.equals("1234")) {
			Cookie cookie = new Cookie("loginMember", m_id);
			cookie.setMaxAge(60 * 30);
			response.addCookie(cookie);
		} else {
			nextPage = "member/cookie/login_ng";
		}
		
		return nextPage;
	}
	
}
