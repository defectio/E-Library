package com.defectio.library.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 모든 요청에 대해서 LoginFilter -> LoginModule을 호출한다.
 * 사이트마다 web.xml을 변경할 수 없으므로 Filter를 직접 이용하지 않고,
 * bean 설정(.xml)파일에서 지정된 LoginModule을 사용한다.
 */
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
}