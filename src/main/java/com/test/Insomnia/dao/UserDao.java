package com.test.Insomnia.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.User;

public interface UserDao extends JpaRepository<User, Integer> {

	@Query(value = "from User where email = :e and password = :p")
	public User findUserLogin(@Param("e") String email, @Param("p") String password);

	@Query(value = "from User where username = :n")
	public User findUserName(@Param("n") String username);
	// @Query(value="select u.name from User u")
	// SELECT e.salary, e.name, e.birthday FROM Employee e

	@Query(value = "from User where email = :e")
	public User findUserEmail(@Param("e") String email);

	// 修改地址
	// 修改或刪除需要加上的註釋，修改的方法只能使用 void 或 int/Integer!
	@Modifying
	@Query(value = "update User set address = :a where id = :i")
	public void updateUserAddress(@Param("a") String address, @Param("i") Integer id);
	
	// 修改密碼
	@Modifying
	@Query(value = "update User set password = :p where id = :i")
	public void updateUserPassword(@Param("p") String password, @Param("i") Integer id);

	@Modifying
	@Query(value = "update User set resetPasswordToken = :r, maturity= :m where id = :i")
	public void resetPassword(@Param("r") String resetPasswordToken, @Param("m") Date maturity, @Param("i") Integer id);

	// 找有沒有這Token
	@Query(value = "from User where resetPasswordToken = :t")
	public User findToken(@Param("t") String resetPasswordToken);

	@Modifying
	@Query(value = "update User set password = :p where id = :i")
	public void resetUser(@Param("p") String password, @Param("i") Integer id);
	
	@Modifying
	@Query(value = "update User set permissions = :per where id = :i")
	public void updateUserPermissions(@Param("per") String permissions, @Param("i") Integer id);
	
}
