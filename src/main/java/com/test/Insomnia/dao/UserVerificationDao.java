package com.test.Insomnia.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.UserVerification;

public interface UserVerificationDao extends JpaRepository<UserVerification, Integer> {
	
	@Query(value = "from UserVerification where verificationToken = :t")
	public UserVerification findToken(@Param("t") String verificationToken);
	
	@Modifying
	@Query(value = "update UserVerification set verificationToken = :t, verificationMaturity= :m where id = :i")
	public void resetPassword(@Param("t") String verificationToken, @Param("m") Date verificationMaturity, @Param("i") Integer id);


}
