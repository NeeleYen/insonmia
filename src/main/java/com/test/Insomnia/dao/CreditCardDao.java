package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.User;

public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {

	@Query(value = "from CreditCard where cardNumber = :n and userId = :u")
	public CreditCard findByNum(@Param("n") Long cardNum, @Param("u") User user);
	
	
//	public CreditCard findByUserIdAndCardNumber(User userId, String cardNumber);
	
	public CreditCard findByCreditCardId(Integer creditCardId);
}
