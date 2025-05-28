package com.example.post.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("Log 필터 생성");
	}

	// 반드시 구현해야 하는 메소드
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("logFilter 실행");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 클라이언트가 요청한 URL 경로 정보를 받아온다.
		String requestURI = httpRequest.getRequestURI();

		try {
			// 사용자가 요청한 경로에 대한 값을 확인
			log.info("requestURI: {}", requestURI);
			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error("필터 실행 중 오류 발생: {}", e.getMessage());
		} finally {
			// 응답 정보 로깅
			log.info("응답 상태 코드: {}", httpResponse.getStatus());
			log.info("응답 컨텐츠 타입: {}", httpResponse.getContentType());
			log.info("응답 헤더 - Content-Leanguage: {}", httpResponse.getHeader("Content-Language"));
			log.info("응답 버퍼 크기: {}", httpResponse.getBufferSize());
		}
	}

	@Override
	public void destroy() {
		log.info("Log 필터 종료");
	}

}
