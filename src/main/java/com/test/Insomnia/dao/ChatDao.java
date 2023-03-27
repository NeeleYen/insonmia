package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Insomnia.model.Chat;

public interface ChatDao extends JpaRepository<Chat, Integer> {

}
