package com.test.Insomnia.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.UserResetPassword;

public interface UserResetPasswordDao extends JpaRepository<UserResetPassword, Integer> {
	
//	@Query(value = "from UserResetPassword where id = :i")
//	public UserResetPassword findId(@Param("i") Integer id);
	
	@Query(value = "from UserResetPassword where resetPasswordToken = :t")
	public UserResetPassword findToken(@Param("t") String resetPasswordToken);
	
	@Modifying
	@Query(value = "update UserResetPassword set resetPasswordToken = :r, maturity= :m where id = :i")
	public void resetPassword(@Param("r") String resetPasswordToken, @Param("m") Date maturity, @Param("i") Integer id);

}
