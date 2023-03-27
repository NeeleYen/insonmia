package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Insomnia.model.Item;

public interface ItemDao extends JpaRepository<Item, Integer> {

}
