package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.UserSocial;

public interface UserSocialDao extends JpaRepository<UserSocial, Integer> {
	
	@Query(value = "from UserSocial where socialSubId = :sub and socialName = :name")
	public UserSocial findByGoogle(@Param("sub")String socialSubId,@Param("name")String googleName);

}
