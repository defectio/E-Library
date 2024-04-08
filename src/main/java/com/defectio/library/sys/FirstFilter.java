package com.defectio.library.sys;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FirstFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("FirstFilter 생성");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("==========First Filter 시작==========");
		chain.doFilter(request, response);
		System.out.println("==========First Filter 종료==========");
	}
	
	@Override
	public void destroy() {
		System.out.println("FirstFilter 종료");
	}

}
