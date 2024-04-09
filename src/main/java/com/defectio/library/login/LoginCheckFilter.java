package com.defectio.library.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PatternMatchUtils;

public class LoginCheckFilter implements Filter {

	/**
	 * [화이트 리스트]
	 *   - 인증 필터를 적용해도 홈, 회원가입, 로그인 화면, css 같은 리소스에응 접근할 수 있어야함.
	 *   - 화이트 리스트 경로는 인증과 무관하게 항상 허용한다.
	 *   - 화이트 리스트를 제외한 나머지 모든 경로에는 인정 체크 로직을 적용한다.
	 */
	private static final String[] WHITELIST = {"/",
			"/library/admin",
			"/library/admin/member/createAccountForm",
			"/library/admin/member/createAccountConfirm",
			"/library/admin/member/loginForm",
			"/library/admin/member/loginConfirm",
			"/library/admin/member/logoutConfirm",
			"/css/*"};
	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String requestURI = req.getRequestURI();

		try {
			logger.info("인증 체크 필터 시작 {}", requestURI);

			// WHITELIST 패턴과 일치하지 않으면
			if (isLoginCheckPath(requestURI)) {
				logger.info("인증 체크 로직 실행 {}", requestURI);
				
				// 로그인 세션이 없을 경우 -> 미로그인 사용자
				HttpSession session = req.getSession(false);
				if (session == null || session.getAttribute("loginedAdminMemberVo") == null) {
					logger.info("미인증 사용자 요청 {}", requestURI);
					// 로그인 페이지로 redirect
					res.sendRedirect("/library/admin/member/loginForm?redirectURI="+requestURI);
//					res.getRequestDispatcher("page").forward(req, res);
					return; // 여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
				}
			}

			// 로그인 사용자만 진행. 
			// 필터 마지막 -> DispatcherServlet 호출 
			chain.doFilter(req, res);
		} catch (Exception e) {
			// 예외 로깅 가능하지만, 톰캣까지 예외를 보내야 함
			throw e; 
		} finally {
			logger.info("인증 체크 필터 종료 {}", requestURI);
		}

	}
	
	/**
	 * 화이트 리스트는 인증 체크 X
	 *   - WHITELIST 패턴과 일치하지 않으면 true/ 일치하면 false
	 *   - requestURI가 WHITELIST패턴과 일치하지 않으면 로그인 체크한다.
	 */
	private boolean isLoginCheckPath(String requestURI) {
		boolean result = !PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
		
		return result;
	}
	
}
