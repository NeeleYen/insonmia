package com.test.Insomnia.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

//參考 https://matthung0807.blogspot.com/2018/07/hibernate-jpa-onetomany-compound-key.html
//官方 https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html#identifiers-composite
//雙主鍵 select https://www.jpa-buddy.com/blog/the-ultimate-guide-on-composite-ids-in-jpa-entities/
//雙主鍵 儲存要有hashcode & equals? https://stackoverflow.com/questions/32240923/hibernate-composite-primary-key-issue-while-saving-entity-object
//雙主鍵建立方式 https://www.baeldung.com/jpa-composite-primary-keys
//hash&equals講解 https://www.gushiciku.cn/pl/gkK1/zh-tw

@Entity
@Table(name = "User_Table")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 255, unique = true) // 要unique不重複，到時候要捕捉重複的例外(ajax)
	private String email;

	@Column(nullable = false, length = 255) // 不能unique，有重複會當掉
	private String password;

	@Nationalized
	@Column(nullable = false, length = 255, unique = true) // 修改成不能重複
	private String username;

	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String address;

	// 權限設定用
	@Column(length = 255, nullable = false)
	private String permissions;

	// 修改密碼的email驗證用
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userId")
	private UserResetPassword userResetPasswordId;

	// 權限設定的驗證機制
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userId")
	private UserVerification userVerification;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userId")
	private UserSocial userGoogle;

	// user去找UserPicture。一對多(雙向)，一中有多，但多中沒有一
	// 主控物件
	// 如果有mappedBy(與@JoinColumn互斥)注意:
	// 有@EmbeddedId的變數名稱.@ManyToOne的變數名稱。一方指向多方，多方中含有一方的屬性名稱。
	// orphanRemoval = true 移除孤兒，false不要刪(預設)
//	@JsonManagedReference
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userPicturePk.userid")
//	private Set<UserPicture> userPicture = new LinkedHashSet<>(); // 看要用哪一種????(決定一下)
	private Set<UserPicture> userPicture = new HashSet<>();

	// user去找CreditCard，雙向一對多
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userId")
//	private Set<CreditCard> creditCards = new LinkedHashSet<>();
	private Set<CreditCard> creditCards = new HashSet<>();

	// user去找bulletin 留言列表(板)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userId")
//	private Set<Bulletin> bulletin = new LinkedHashSet<>();
	private Set<Bulletin> bulletin = new HashSet<>();

	// (已改雙向，不知用set是否會較佳) user去找Product(Product list)
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
//	@JoinColumn(name = "FK_UserId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_product"))
//	private Set<Product> product = new LinkedHashSet<>();
//	private Set<Product> product = new LinkedHashSet<>(0);
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Product> product = new HashSet<>();

	// user去找訂單 雙向
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userId")
//	private Set<Order> order = new LinkedHashSet<>();
	private Set<Order> order = new HashSet<>();

	// user去找發言者 雙向
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userIdSender")
//	private Set<Order> order = new LinkedHashSet<>();
	private Set<Chat> sender = new HashSet<>();

	// user去找收言者 雙向
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "userIdReceiver")
//	private Set<Order> order = new LinkedHashSet<>();
	private Set<Chat> receiver = new HashSet<>();

	public User() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public UserResetPassword getUserResetPasswordId() {
		return userResetPasswordId;
	}

	public void setUserResetPasswordId(UserResetPassword userResetPasswordId) {
		this.userResetPasswordId = userResetPasswordId;
	}

	public UserVerification getUserVerification() {
		return userVerification;
	}

	public void setUserVerification(UserVerification userVerification) {
		this.userVerification = userVerification;
	}

	public UserSocial getUserGoogle() {
		return userGoogle;
	}

	public void setUserGoogle(UserSocial userGoogle) {
		this.userGoogle = userGoogle;
	}

	public Set<UserPicture> getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(Set<UserPicture> userPicture) {
		this.userPicture = userPicture;
	}

	public Set<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Set<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	public Set<Bulletin> getBulletin() {
		return bulletin;
	}

	public void setBulletin(Set<Bulletin> bulletin) {
		this.bulletin = bulletin;
	}

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	public Set<Chat> getSender() {
		return sender;
	}

	public void setSender(Set<Chat> sender) {
		this.sender = sender;
	}

	public Set<Chat> getReceiver() {
		return receiver;
	}

	public void setReceiver(Set<Chat> receiver) {
		this.receiver = receiver;
	}

	public User(Integer id, String email, String password, String username, String address, String permissions,
			UserResetPassword userResetPasswordId, UserVerification userVerification, UserSocial userGoogle,
			Set<UserPicture> userPicture, Set<CreditCard> creditCards, Set<Bulletin> bulletin, Set<Product> product,
			Set<Order> order, Set<Chat> sender, Set<Chat> receiver) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.address = address;
		this.permissions = permissions;
		this.userResetPasswordId = userResetPasswordId;
		this.userVerification = userVerification;
		this.userGoogle = userGoogle;
		this.userPicture = userPicture;
		this.creditCards = creditCards;
		this.bulletin = bulletin;
		this.product = product;
		this.order = order;
		this.sender = sender;
		this.receiver = receiver;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + permissions));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
