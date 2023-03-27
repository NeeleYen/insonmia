package com.test.Insomnia.springsecurity.oauth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.dao.UserSocialDao;
import com.test.Insomnia.model.User;
import com.test.Insomnia.model.UserSocial;

@Service
public class OAuthUserService extends DefaultOAuth2UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserSocialDao userGoogleDao;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//		登入的時候先抓到是哪一個社群~google/github
		String registrationId = userRequest.getClientRegistration().getRegistrationId().trim();
		String nameAttributeKey = null;

		OAuth2User oauth2User = super.loadUser(userRequest);
//		github專用
		if (registrationId.equals("github")) {
			String clientName = (oauth2User.getName()).trim(); // ID
			String attribute = oauth2User.getAttribute("login"); // 帳號、名字
			nameAttributeKey = "login";
			UserSocial findByGoogle = userGoogleDao.findByGoogle(clientName.trim(), attribute.trim());
//			幫他建立User
			if (findByGoogle == null) {
				User user = new User();
				String generatedString = UUID.randomUUID().toString(); // 隨機產生密碼
				user.setUsername(attribute);
				user.setEmail(attribute);
				user.setPassword(generatedString); // 隨機產生的
				user.setPermissions("member"); // 給他權限
				user.setAddress(" ");// 地址用空格表示
				userDao.save(user);

				UserSocial userGoogle = new UserSocial();
				userGoogle.setUserId(user);
				userGoogle.setSocialSubId(clientName);
				userGoogle.setSocialName(attribute);
				userGoogleDao.save(userGoogle);
			}
		} else if (registrationId.equals("google")) {
			String clientName = oauth2User.getAttribute("name"); // ID
			String attribute = oauth2User.getAttribute("sub"); // 帳號、名字
			nameAttributeKey = "sub";
			UserSocial findByGoogle = userGoogleDao.findByGoogle(clientName.trim(), attribute.trim());
//			幫他建立User
			if (findByGoogle == null) {
				User user = new User();
				String generatedString = UUID.randomUUID().toString(); // 隨機產生密碼
				user.setUsername(clientName);
				user.setEmail(attribute);
				user.setPassword(generatedString); // 隨機產生的
				user.setPermissions("member"); // 給他權限
				user.setAddress(" ");// 地址用空格表示
				userDao.save(user);
				UserSocial userGoogle = new UserSocial();
				userGoogle.setUserId(user);
				userGoogle.setSocialSubId(clientName);
				userGoogle.setSocialName(attribute);
				userGoogleDao.save(userGoogle);
			}
		}
		Map<String, Object> attributes = oauth2User.getAttributes();
//		設定權限
		Collection<? extends GrantedAuthority> authorities = Collections
				.singleton(new SimpleGrantedAuthority("ROLE_member"));
		OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, nameAttributeKey);
		// sub 是 Google 的 OAuth2 認證中用來代表使用者的唯一 ID 字段，因此在示例中使用了sub來代表這個值。
		return userDetails;
	}
}
