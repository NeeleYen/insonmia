package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Insomnia.model.Bulletin;

public interface BulletinDao extends JpaRepository<Bulletin, Integer> {

//	Bulletin findByProductIdAndUserId(Product product, User userId);
}
