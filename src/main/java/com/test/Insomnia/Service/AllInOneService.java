package com.test.Insomnia.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;

@Service
@Transactional
public class AllInOneService {

	public AllInOne newAllInOne() {
		return new AllInOne("");
	}
}
