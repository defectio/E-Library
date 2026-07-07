package com.defectio.library.admin.member;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	/**
	 * 회원 가입 페이지로 이동
	 * 
	 * @RequestMapping(value = "/createAccountForm", method = RequestMethod.GET) 와 동일 
	 * @return
	 */
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		String nextPage = "admin/member/create_account_form";
		return nextPage;
	}
	
	/**
	 * client에서 회원 가입 클릭 시 회원 가입 처리를 진행한다.
	 * @param adminMemberVo
	 * 
	 * @RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST) 동
	 * @return
	 */
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		String nextPage = "admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		// 이미 존재하는 ID or 회원가입 실패할 경우, 페이지 변경 
		if (result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
	}
	
	/**
	 *  로그인 페이지 이동
	 * @return
	 */
	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "admin/member/login_form";
		return nextPage;
	}
	
	/**
	 * Cookie 방식 로그인 처리
	 *   - super_admin / 1234
	 * @param adminMemberVo
	 * @return
	 */
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpServletResponse response) {
		
		/**
		 * 클라이언트에서 넘어오는 a_m_id, a_m_pw가 getter를 통해서 adminMemberVo에 저장됨
		 * 클라이언트에서 넘어오지 않는 a_m_mail은 null이 저장됨
		 */
//		System.out.println("id >> "+ adminMemberVo.getA_m_id());
//		System.out.println("pw >> "+ adminMemberVo.getA_m_pw());
//		System.out.println("mail >> "+ adminMemberVo.getA_m_mail());
		
		String nextPage = "login_ok";
		
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
		
		// 로그인 시도한 사용자가 없는 경우, 즉 회원이 아닌 경우 리턴 페이지 재정의
		if (loginedAdminMemberVo == null) {
			nextPage = "login_ng";
		} else {
			Cookie cookie = new Cookie("loginMember", loginedAdminMemberVo.getA_m_id());
			cookie.setMaxAge(60 * 30);  // 유효시간 설정(초) : 30분
			response.addCookie(cookie);  // 생성된 쿠키를 response객체에 추가하여 클라이언트에 전달한다.
		}
		
		// nav.jsp를 include 하기 때문에 redirect로 변경
		return "redirect:"+nextPage;
	}
	
	/**
	 *  로그인 성공 페이지 이동
	 * @return
	 */
	@GetMapping("/login_ok")
	public String loginOk() {
		String nextPage = "admin/member/login_ok";
		return nextPage;
	}

	/**
	 *  로그인 실패 페이지 이동
	 * @return
	 */
	@GetMapping("/login_ng")
	public String loginNg() {
		String nextPage = "admin/member/login_ng";
		return nextPage;
	}
	
	/**
	 * 로그아웃
	 * @param loginMember 
	 * @param response
	 * @return
	 */
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(@CookieValue(value="loginMember", required=false) String loginMember, HttpServletResponse response) {
		/**
		 * @CookieValue
		 * 클라이언트가 서버에 요청을 하면 자동으로 쿠키가 전달된다. 서버에서는 @CookieValue 사용하여 쿠키를 받을 수 있다.
		 *  - value : 쿠키이름
		 *  - required : false(쿠키가 필수값이 아님을 선언). required를 명시하지 않으면 쿠키가 없을 경우, 예외가 발생하기 때문에 false로 설정
		 *  - String loginMember : 클라이언트에서 전달된 쿠키가 loginMember에 저장됨
		 */
		Cookie cookie = new Cookie("loginMember", loginMember);
		cookie.setMaxAge(0); // 쿠키 유효기간 종료

		response.addCookie(cookie);
		
		return "redirect:/admin";
	}
	
	/**
	 * 관리자 목록 출력
	 * @param model
	 * @return
	 */
	@GetMapping("/listupAdmin")
	public String listupAdmin(Model model) {
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
		
		/**
		 * model 객체
		 *   - name : 전달하려는 데이터의 이름
		 *   - value : 실제 데이터 값
		 */
		model.addAttribute("adminMemberVos", adminMemberVos);
		
		return nextPage;
	}
	
	/**
	 * 관리자 승인 후 관리자 목록 redirect
	 */
	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		String nextPage = "redirect:/admin/member/listupAdmin";
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
	}
	
	/**
	 * modifyAccountForm 페이지를 리턴한다.
	 */
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session)	 {
		/**
		 * 세션 체크 해서 로그인된 사용자인지 판단 해야함.
		 *   - 로그인 하지 않았다면 로그인 페이지로 이동
		 */
		AdminMemberVo loginedMemverVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		
		/**
		 *  세션이 null인 경우
		 *    (1) 로그인 하지 않고 url로 직접 접근하는 경우
		 *    (2) 로그인 후 세션 만료 기간이 지나 세션이 사라진 경우
		 */
		if (loginedMemverVo != null) {
			return "admin/member/modify_account_form";
		}
		// login_form 재요청
		return "redirect:/admin/member/loginForm";
	}
	
	/**
	 * 관리자 계정을 수정한다.
	 */
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		
		/**
		 * 관리자 계정이 정상적으로 수정 되었다면, 수정된 정보로 로그인 시킨다.
		 */
		int result = adminMemberService.modifyAccountConfirm(adminMemberVo);
		if (result > 0) { // 성공
			AdminMemberVo loginedAdmin = adminMemberService.getLoginedAdminMemberVo(adminMemberVo.getA_m_no());
			
			session.setAttribute("loginedAdminMemberVo", loginedAdmin);
			session.setMaxInactiveInterval(60 * 30);
			
			return "admin/member/modify_account_ok";
		} else {
			return "admin/member/modify_account_ng";
		}
	}
	
	/**
	 * 관리자 비밀번호 찾기 폼을 리턴한다.
	 */
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		return "admin/member/find_password_form";
	}
	
	/**
	 * id, 이름, 메일 주소를 받고 관리자와 일치하는 메일 주소로 새로운 비밀번호를 전송한다.
	 */
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(AdminMemberVo adminMemberVo) {
		int result = adminMemberService.findPasswordConfirm(adminMemberVo);
		if (result > 0) {
			return "admin/member/find_password_ok";
		} else {
			return "admin/member/find_password_ng";
		}
	}
}