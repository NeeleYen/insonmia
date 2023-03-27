package com.test.Insomnia.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Area_Table")
public class Area {	//先訂為大陸分區 BY顏

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer areaId;

	@Nationalized	//把字串弄成nvarchar
	@Column(nullable = false, length = Integer.MAX_VALUE)
	private String area;
//    public static final List<String> AREA_OPTIONS = Arrays.asList(AreaConstants.TW, AreaConstants.JP, AreaConstants.US);
//	已有資料庫，為避免與資料庫資料有出入，棄用
//	public static final Map<Integer, String> AREA_OPTIONS = new HashMap<>() {	
//		private static final long serialVersionUID = 1L;
//		{
//			put(1, AreaConstants.TW);
//			put(2, AreaConstants.JP);
//			put(3, AreaConstants.US);
//		}
//	};

	
	@ManyToMany(mappedBy = "areaNo")
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchasePlan")
	@JsonIgnore
	private Set<PurchasePlan> purchasePlan = new HashSet<PurchasePlan>(0);

//	@JsonManagedReference
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "order")
	@JsonIgnore
	@OneToMany(mappedBy = "areaId", cascade = CascadeType.ALL, orphanRemoval = false) // 當一方被刪除時，另一方不會被刪除(預設為false)
	private Set<Order> order = new HashSet<Order>(0);

	public Area() {
	}

	public Area(Integer areaId, String area, Set<PurchasePlan> purchasePlan, Set<Order> order) {
		super();
		this.areaId = areaId;
		this.area = area;
		this.purchasePlan = purchasePlan;
		this.order = order;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Set<PurchasePlan> getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(Set<PurchasePlan> purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

}
