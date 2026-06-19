package com.Dummy.Dummy.Controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.Dummy.Dummy.beans.Users;

import ch.qos.logback.core.model.Model;

@Controller
public class PaymentPageController {

	@GetMapping("/paymentPage")
	public String paymentPage() {
		
		int[] a=new int[5];
		int length = a.length;
		Users principal = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("user name : " + principal.getUsername());
		return "paymentPage";
	}

	@GetMapping("/login")
	public String login(org.springframework.ui.Model model) {
		model.addAttribute("users", new Users());
		return "login";
	}

}
