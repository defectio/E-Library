package com.defectio.library.login;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * [Filter]
 *   - 필터를 사용하려면 Filter 인터페이스를 구현해야 한다.
 *   - Filter의 메소드
 *   	(1) init : 필터 초기화 메서드
 *   	(2) doFilter : 요청이 올 때마다 해당 메서드가 호출된다. 필터 로직 구현부
 *   	(3) destroy : 필터 종료 메서드
 *   
 * [Filter 흐름]
 *   - HTTP요청 -> WAS -> 필터 -> Dispatcher Servlet -> Controller
 *   - 필터가 호출된 다음에 Dispatcher Servlet이 호출된다. 즉, 모든 고객의 요청 로그를 남기는 요구사항이 있다면 필터를 사용하면 된다.
 *   - url 패턴 : /* 로 하면 모든 요청에 필터가 적용된다. 
 *   - 필터 체인을 이용하여 여러개의 필터를 사용할 수도 있다.
 *   - HTTP요청 -> WAS -> 필터1 -> 필터2 -> 필터3 -> Dispatcher Servlet -> Controller
 * 
 * [LoginFilter]
 *   - 회원(로그인 사용자)만 글쓰기, 수정, 삭제 기능을 사용할 수 있다.(액션 실행 전 로그인 확인 로직 필요)
 *   - 공통의 관심사는 스프링의 AOP로 해결할 수 있지만, 웹과 관련된 공통의 관심사는 서블릿 필터 또는 스프링 인터셉터를 사용하는 것이 좋다.
 *   - 참고 : https://hyuuny.tistory.com/3
 * 
 */
public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	/**
	 * 필터 초기화 메서드
	 *   - 서블릿 컨테이너가 생성될 때 호출된다.(default 메서드이기 때문에, 구현하지 않아도 됨.)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("LoginFilter init()");
	}

	/**
	 * 필터 종료 메서드
	 *   - 서블릿 컨테이너가 종료될 때 호출된다.(default 메서드이기 때문에, 구현하지 않아도 됨.)
	 */
	@Override
	public void destroy() {
		logger.info("LoginFilter init()");
	}

	/**
	 * 필터 로직 구현부
	 *   - HTTP 요청이 오면 doFilter가 호출된다.
	 *   - ServletRequest request : HTTP 요청이 아닌 경우가지 고려해서 만들어진 인터페이스임
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("LoginFilter doFilter()");
		
		// ServletRequest -> HttpServletRequest casting
		HttpServletRequest req = (HttpServletRequest) request;
		String requestURI = req.getRequestURI();
		String uuid = UUID.randomUUID().toString(); // HTTP 요청을 구분하기 위한 요청당 임의의 uuid 생성

		try {
			logger.info("REQUEST [{}][{}]", uuid, requestURI);
			
			/**
			 * chain.doFilter(request, response)
			 *   - 다음 필터가 있으면 필터를 호출하고, 필터가 없으면 Dispatcher Servlet을 호출한다.
			 *   - doFilter() 메소드를 호출하지 않으면 다음단계로 진행되지 않음.
			 */
			chain.doFilter(req, response);
		} catch (Exception e) {
			throw e;
		} finally {
			logger.info("RESPONSE [{}][{}]", uuid, requestURI);
		}
	}

}