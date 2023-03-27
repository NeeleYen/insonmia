package com.test.Insomnia.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.CreditCardDao;
import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.User;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardDao creditCardDao;
	
	public CreditCard findCardByNum(Long cardNum, User user) {
		CreditCard creditCard = creditCardDao.findByNum(cardNum, user);
		return creditCard;
	}
	
	public CreditCard findById(Integer creditCardId) {
		CreditCard findByCreditCardId = creditCardDao.findByCreditCardId(creditCardId);
		return findByCreditCardId;
	}

	
}
