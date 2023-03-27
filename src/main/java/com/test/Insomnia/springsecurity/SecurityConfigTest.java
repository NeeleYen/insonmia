package com.test.Insomnia.springsecurity;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.test.Insomnia.springsecurity.oauth.OAuthUserService;

@EnableWebSecurity
@Configuration
public class SecurityConfigTest {

	@Autowired
	UserDetailsService userdetailsService; // 要加private?

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Autowired
	private OAuthUserService authUserService;

	// 注入數據-cookie
	@Autowired
	private DataSource dataSource;
	
//	// 配置對象-cookie
	@Bean
	PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
//		注意要改成datetime。(資料庫的地方)
//		jdbcTokenRepositoryImpl.setCreateTableOnStartup(true); // 他幫我們創建表格，創一次之後就關掉
		return jdbcTokenRepositoryImpl;
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new MyUserDetailService();
	}

	// 要有PasswordEncoder，如果沒有創建就會報錯
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 配置請求哪些資源不需要做驗證
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> web.ignoring().antMatchers("/css/**", "/js/**");
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 配置自定義沒有權限訪問跳轉頁面，應該要跳轉到請他去收信件驗證
		http.exceptionHandling().accessDeniedPage("/public/unchecked");

		http.oauth2Login().loginPage("/public/signIn").defaultSuccessUrl("/").permitAll().userInfoEndpoint()
				.userService(authUserService)

				.and().and().formLogin() // 表單自定義的編寫的登入頁面
				.loginPage("/public/signIn") // 登入頁面設置，登入頁面的地址
				.loginProcessingUrl("/public/user/logIn") // 登入訪問的路徑，丟到哪一個控制器中，但他說不用管(有內建)
				.defaultSuccessUrl("/").permitAll() // 登入成功之後跳轉的路徑，並允許他的操作

				.failureUrl("/public/user/signInError").and().authorizeRequests()
				.antMatchers("/signIn.jsp", "/addAll", "/public/**", "/addAll/Lisa", "/image/**","/images/**").permitAll() // 那些需要認證，那些不需要認證
				.antMatchers("/product/**").hasRole("member") // 一定要加上一個ROLE_sale，這些是要放有權限才可以進去的會員!!!!!!!!
				.anyRequest().authenticated()
				.and()
					.rememberMe()
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(60*60*24*7) // 設置有效時間，以秒為單位
					.rememberMeParameter("remember-me")
					.userDetailsService(myUserDetailService)
				.and()
					.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.logoutSuccessUrl("/").permitAll()
						.deleteCookies("myCookie")		
				.and().csrf().ignoringAntMatchers("/public/product/payResult") // 開csrf
				.and().userDetailsService(myUserDetailService); // 開csrf
		return http.build();
	}

	/*
	 * 這段代碼的作用是配置 Spring Security，設置應用程式的登入、授權、記住我、登出等功能。 這裡是每一行的意思： http: 定義 HTTP
	 * 安全配置 .formLogin(): 啟用表單登入功能 .loginPage("/public/signIn"): 設置自訂登入頁面的 URL
	 * .loginProcessingUrl("/public/user/logIn"): 設置表單提交的 URL，用於處理登入請求
	 * .defaultSuccessUrl("/").permitAll(): 設置預設登入成功後的 URL，並允許所有使用者訪問
	 * .failureUrl("/public/user/signInError"): 設置登入失敗後跳轉的 URL
	 * .and().authorizeRequests(): 定義對請求的授權策略 .antMatchers("/signIn.jsp", "/addAll",
	 * "/public/**", "/addAll/Lisa", "/image/**").permitAll(): 設置特定 URL 可以被所有人訪問
	 * .antMatchers("/product/add").hasRole("member"): 設置需要 "member" 角色才能訪問
	 * "/product/add" URL .anyRequest().authenticated(): 定義所有未被前面設置過的 URL 都需要驗證後才能訪問
	 * .and().oauth2Login(): 啟用 OAuth2 登入功能
	 * .and().rememberMe().tokenRepository(persistentTokenRepository()):
	 * 啟用記住我功能，並設置記住我 Token 的存儲方式 .tokenValiditySeconds(30): 設置記住我 Token 的有效時間，單位為秒
	 * .rememberMeParameter("remember-me"): 設置記住我功能在表單中的參數名稱
	 * .userDetailsService(myUserDetailService): 設置 UserDetailsService 來查詢用戶信息
	 * .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout",
	 * "GET")).logoutSuccessUrl("/").permitAll(): 啟用登出功能，並設置登出 URL、登出成功後跳轉的
	 * URL，以及允許所有人訪問登出 URL .deleteCookies("myCookie"): 設置登出時需要刪除的 Cookie
	 * .and().userDetailsService(myUserDetailService): 設置 UserDetailsService 來查詢用戶信息
	 * return http.build();: 將 HTTP 安全配置建立為 SecurityFilterChain 物件並返回
	 */

}
