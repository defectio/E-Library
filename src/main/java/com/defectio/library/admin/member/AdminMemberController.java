package com.defectio.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	 * @return
	 */
//	@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		String nextPage = "admin/member/create_account_form";
		return nextPage;
	}
	
	/**
	 * client에서 회원 가입 클릭 시 회원 가입 처리를 진행한다.
	 * @param adminMemberVo
	 * @return
	 */
//	@RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	// @RequestMapping과 method를 결합한 어노테이션
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		String nextPage = "admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
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
	 * 로그인 확인
	 *   - super_admin / p9AAISxf
	 * @param adminMemberVo
	 * @return
	 */
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		
		/**
		 * 클라이언트에서 넘어오는 a_m_id, a_m_pw가 getter를 통해서 adminMemberVo에 저장됨
		 * 클라이언트에서 넘어오지 않는 a_m_mail은 null이 저장됨
		 */
//		System.out.println("id >> "+ adminMemberVo.getA_m_id());
//		System.out.println("pw >> "+ adminMemberVo.getA_m_pw());
//		System.out.println("mail >> "+ adminMemberVo.getA_m_mail());
		
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
		
		if (loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
		} else {
			// 로그인 성공 -> 세션에 로그인 정보 지정 (key - value)
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			/**
			 *  세션 유효기간(초) -> 30분
			 *  즉, 브라우저가 30분동안 아무런 동작을 하지 않게 되면 서버의 세션은 종료되고 관리자는 다시 로그인 해야 한다.
			 */
			session.setMaxInactiveInterval(60 * 30);   
		}
		
		return nextPage;
	}
	
	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		String nextPage = "redirect:/admin";
		
		/**
		 * 세션 무효화
		 *   - 세션에 저장된 데이터(loginedAdminMemberVo)를 사용할 수 없게 한다.
		 *   - 로그인 상태는 해제되고, /admin으로 redirect된다.
		 *   -> /admin으로 redirect : AdminHomeController의 home() 메소드 호출 : admin/home 페이지 이동
		 */
		session.invalidate();
		
		return nextPage;
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