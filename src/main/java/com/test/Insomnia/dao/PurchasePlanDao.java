package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Insomnia.model.PurchasePlan;

public interface PurchasePlanDao extends JpaRepository<PurchasePlan, Integer> {
}
