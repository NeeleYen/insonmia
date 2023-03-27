package com.test.Insomnia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.Insomnia.dao.FAQDao;
import com.test.Insomnia.model.FAQ;

@Controller
public class FAQController {
	
	@Autowired
	private FAQDao faqDao;
	
	@GetMapping("/public/faq/FAQ")
	public String FAQPage() {
		return "/faq/FAQ";
	}
	
	@ResponseBody
	@PostMapping("/faq/add")
	public FAQ saveCustomer2(@RequestBody FAQ faq) {
		return faqDao.save(faq);
	}
	
	@ResponseBody
	@GetMapping("/faq/id")
	public FAQ findById(@RequestParam("id") Integer id) {
		Optional<FAQ> optional = faqDao.findById(id);
		
		if(optional.isPresent()) {
			FAQ faq = optional.get();
			return faq;
		}
		return null;
	}
	
	@ResponseBody
	@GetMapping("/faqs")
	public List<FAQ> findAll(){
		return faqDao.findAll();
	}
	
	@ResponseBody
	@DeleteMapping("/faq/delete")
	public String deleteById(@RequestParam Integer id) {
		try {
			faqDao.deleteById(id);
			return "刪除成功";
		}catch(EmptyResultDataAccessException e){
			return "沒有這筆資料";
		}
	}
	
}
