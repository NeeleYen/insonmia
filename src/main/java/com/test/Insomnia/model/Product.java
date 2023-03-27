package com.test.Insomnia.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Product_Table") // ProductList
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer PId;

	@Nationalized
	@Column(nullable = false, length = 255)
	private String title;

	// 要字串還是數字? 要處理一下
	// 記得上次說SQL用數字 BY顏
	@Column(nullable = false)
	private Integer category;
	public static final Map<Integer, String> CATEGORY_OPTIONS = new HashMap<>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, ProductCategoryConstants.MUSIC);
			put(2, ProductCategoryConstants.ART);
			put(3, ProductCategoryConstants.TRAVEL);
			put(4, ProductCategoryConstants.GAME);
			put(5, ProductCategoryConstants.CRAFT);
		}
	};
	

//	faq欄位改用table
//	@Nationalized
//	@Column(length = Integer.MAX_VALUE)
//	private String faq;

	// 不確定都會是default 0
	// 產品動態對應數字未定 BY顏
	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer status = 0;

	// 詳細介紹 BY顏
	@Nationalized
	@Column(length = Integer.MAX_VALUE)
	private String intro;

	// 時間部分不確定
	@Temporal(TemporalType.TIMESTAMP) // 如果用SQL date 就不用寫這個，但就不知道這是什麼
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 只能在java環境做設定，大小寫嚴格不同意義
//	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE", timezone = "GMT+8") // 轉乘JSON才吃的到
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 轉乘JSON才吃的到、配合html date picker pattern
	@Column(columnDefinition = "datetime", nullable = false)
	private Date closeDate;

	// 專案發起人 BY顏
	// product
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FK_UserId", nullable = false, foreignKey = @ForeignKey(name = "fk_user_product"))
	private User user;

//	=====oneToMany均為先建立product後再事後新增=====

	// 產品圖片
	@JsonIgnore
//	(@JsonManagedReference 做啥用的?)
//	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "picId.productId")
	private Set<ProductPicture> productPicture = new HashSet<ProductPicture>(0);

	// 購買方案
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = false)
//	@JoinColumn(name = "FK_ProductId", referencedColumnName = "P_id", foreignKey = @ForeignKey(name = "fk_ProductId_purchasePlan"), nullable = false)
//	private Set<PurchasePlan> purchasePlan = new LinkedHashSet<>();
	private Set<PurchasePlan> purchasePlan = new HashSet<PurchasePlan>(0);

	// 貨品列表
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "productId")
	private Set<Item> item = new HashSet<Item>(0);

	// 一對多留言板(雙向)
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "product")
	private List<Bulletin> bulletin = new ArrayList<>();

	// faq table
//	TODO json reference
//	https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
//	@JsonManagedReference(value="product-faqs")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "productId")
	private Set<FAQ> faqId = new HashSet<>();

	public Product() {
	}

	public Integer getPId() {
		return PId;
	}

	public void setPId(Integer pId) {
		PId = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<ProductPicture> getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(Set<ProductPicture> productPicture) {
		this.productPicture = productPicture;
	}

	public Set<PurchasePlan> getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(Set<PurchasePlan> purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	public Set<Item> getItem() {
		return item;
	}

	public void setItem(Set<Item> item) {
		this.item = item;
	}

	public List<Bulletin> getBulletin() {
		return bulletin;
	}

	public void setBulletin(List<Bulletin> bulletin) {
		this.bulletin = bulletin;
	}

	public Set<FAQ> getFaqId() {
		return faqId;
	}

	public void setFaqId(Set<FAQ> faqId) {
		this.faqId = faqId;
	}

	public Product(Integer pId, String title, Integer category, Integer status, String intro, Date closeDate, User user,
			Set<ProductPicture> productPicture, Set<PurchasePlan> purchasePlan, Set<Item> item, List<Bulletin> bulletin,
			Set<FAQ> faqId) {
		super();
		PId = pId;
		this.title = title;
		this.category = category;
		this.status = status;
		this.intro = intro;
		this.closeDate = closeDate;
		this.user = user;
		this.productPicture = productPicture;
		this.purchasePlan = purchasePlan;
		this.item = item;
		this.bulletin = bulletin;
		this.faqId = faqId;
	}

}
