package com.test.Insomnia.controller.daoTesting;
//package com.test.Insomnia.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.test.Insomnia.Service.UserService;
//import com.test.Insomnia.dto.TestBean;
//import com.test.Insomnia.model.Product;
//import com.test.Insomnia.model.User;
//
//@Controller
//public class TestController {
//	
//	@Autowired
//	private UserService userService;
//	// 自postman發送post請求。讓user新增product資料。
//	@RequestMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
////	@PostMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public TestBean addProduct(@RequestBody TestBean testBean) {
////		get user data from session, how?
////		只有controller可以叫service做事
////		User user = userService.findById(1);
////		if( user != null ) {
////			product.setUser(user);
////		}
////		在controller包好物件，再傳給service處理資料。service處理完資料再請求底層Dao儲存。
//		System.out.println(testBean.getStr());
//		return testBean;
//	}
//}
