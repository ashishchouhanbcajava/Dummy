//package com.Dummy.Dummy.configurations;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//	@Autowired
//	private Interceptor interceptor;
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		// TODO Auto-generated method stub
//		System.out.println("in cors config");
//		// here '/**' means the cors configuration is applied to all the sengments of
//		// the path endpoints like /employee/add .....
//		// and if this was '/*' then it is applied to a single path segment of a
//		// endpoint like /employee
//		registry.addMapping("/**")
//				.allowedOrigins("http://localhost:8085", "http://localhost:8089", "http://127.0.0.1:5500")
//				.allowedMethods("*");
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// TODO Auto-generated method stub
//		System.err.println("in add interceptor method ");
//		InterceptorRegistration interceptor2 = registry.addInterceptor(interceptor);
//
//		interceptor2.addPathPatterns("/employees/save");
//	}
//
//}
