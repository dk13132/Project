package mc.login.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import mc.login.service.LoginService;

@Controller
@Setter
@RequestMapping("/login.do")
public class LoginController {
	@Autowired
	LoginService service;
	
	@GetMapping
	public String loginForm() {
		return "login";
	}
	
	@PostMapping
	public ModelAndView loginPro(String employee_no, String password, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Map<String, String> map = service.login(employee_no);
		
		if(map == null) {
			mav.addObject("check", 0);
			mav.setViewName("login");
		}
		else if(!map.get("password").equals(password)) {
			mav.addObject("check", 1);
			mav.setViewName("login");
		}
		else {
			request.getSession().setAttribute("employee_no", employee_no);
			request.getSession().setAttribute("name", map.get("name"));
			mav.setViewName("main");
		}
		return mav;
	}
}
