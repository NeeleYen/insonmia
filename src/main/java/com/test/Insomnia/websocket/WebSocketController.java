package com.test.Insomnia.websocket;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ImportResource("classpath:springmvc-servlet.xml")
public class WebSocketController {	
	//即時聊天室
	//http://localhost:8080/ws/ws-chat
	@RequestMapping(value="/ws-chat")
	public String wsChat(HttpSession session) {
		//使用者尚未通過身分驗證
		if(session.getAttribute("authenticated") == null) { 			
			return "redirect:/wsLoginForm.html";
		}
		else
			return "wsChat";		
	}
	@RequestMapping(value="/wsLogin")
	public String wsLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {		
		if(username.contains("小倩")) {
			session.setAttribute("authenticated", username);			
			return "wsChat";			
		}
		else
			return "redirect:/wsLoginForm.html";
	}

}
