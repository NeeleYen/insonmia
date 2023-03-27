package com.test.Insomnia.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.FAQDao;
import com.test.Insomnia.model.FAQ;

@Service
@Transactional
public class FaqService {
	
	@Autowired
	private FAQDao faqDao;
	
	public FAQ insert( FAQ faq ) {
		if ( faq.getAnswer().isBlank() || faq.getQuestion().isBlank() || faq.getProductId() == null ) {
			return null;
		}
		faq.setAnswer(faq.getAnswer().trim());
		faq.setQuestion(faq.getQuestion().trim());
		return faqDao.save(faq);
	}
	
	public FAQ findFAQById( Integer id ) {
		Optional<FAQ> option = faqDao.findById(id);
		if ( option.isEmpty() ) {
			return null;
		}
		FAQ faq = option.get();
		return faq;
	}
	
	public void delete( FAQ faq ) {
		if ( faq == null ) {
			return ;
		}
		faqDao.delete(faq);
	}
}
