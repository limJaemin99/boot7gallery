package org.iclass.mvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
//session 값 저장을 위해서 테스트용 로그인
	
	@GetMapping("/login")   //실행은 http://localhost:8082/login?userid=momo99
	public String login(String userid,HttpSession session) {
		if(userid != null)		//테스트를 위해 userid 파라미터를 세션에 저장하기
			session.setAttribute("userid", userid);		//정상적으로는 postmapping 에서 합니다.
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
