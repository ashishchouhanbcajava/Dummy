package com.Dummy.Dummy.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class Interceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("i m in handler ");

		String header = request.getHeader("employeeId");
		if (header.equals("101")) {
			throw new RuntimeException("id is invalid ");
		}

		request.setAttribute("executionTime", System.currentTimeMillis());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

//		response.get
		System.out.println("i m in post handle ");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		Long executionTime = (long) request.getAttribute("executionTime");
		long timeMillis = System.currentTimeMillis() - executionTime;

		System.out.println("execution time : " + timeMillis);

	}

}
