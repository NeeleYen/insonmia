package com.test.Insomnia.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.Insomnia.dao.UserDao;


@Service // ("userDetailService")
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.test.Insomnia.model.User findUserEamil = userDao.findUserEmail(email);
		if (findUserEamil == null) {
			throw new UsernameNotFoundException("用戶名不存在");
		}
		
		return new User(findUserEamil.getEmail(), findUserEamil.getPassword(), findUserEamil.getAuthorities());
	}

}
