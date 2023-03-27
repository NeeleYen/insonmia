package com.test.Insomnia.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.Order;
import com.test.Insomnia.model.User;

public interface OrderDao extends JpaRepository<Order, Integer> {

	//設為取消狀態
	@Modifying
	@Query(value = "update Order o set o.cancelStatus = :c where o.id = :id")
	public void setOrderCancelStatus(@Param("c") Integer cancelStatus, @Param("id") Integer id);
	
	//取消訂單時購買方案的訂單數減
//	@Modifying
//	@Query(value = "update PurchasePlan pp set pp.currentOrderAmount = :ppa "
//			+ "where pp.purchasePlanId = :id")
//	public void setCurrentOrderAmount(@Param("ppa") Integer adjustedAmount, 
//			@Param("id") Integer purchasePlanId);
	
	//找id最大的order
	public Order findFirstByOrderByOrderIdDesc();
	
	//設定付款狀態
	@Modifying
	@Query(value = "update Order o set o.paymentStatus = :p where o.id = :id")
	public void setPaymentStatus (@Param("p") Integer status, @Param("id") Integer id);

	//募資狀態
	@Query(value = "SELECT SUM(Order_Table.price) "
			+ "FROM PurchasePlan_Table "
			+ "INNER JOIN Order_Table "
			+ "ON PurchasePlan_Table.purchasePlanId=Order_Table.fk_PurchasePlan "
			+ "where PurchasePlan_Table.FK_ProductId= :pid and Order_Table.cancelStatus= :c", nativeQuery = true)
	public Integer sumOrderPriceByProductId(@Param("pid") Integer pid, @Param("c") Integer cancelStatus);
	
	//找該user所有order做分頁
	@Query(value = "from Order o where o.userId = :userId")
	public Page<Order> findAllByUser(@Param("userId") User userId, Pageable pageable);
	
	@Query(value = "SELECT SUM(o.amount) from Order o where o.purchasePlan.purchasePlanId = :PlanId and o.cancelStatus = 0")
	public Integer sumOfOrderedAmountByPurchasePlan(@Param("PlanId") Integer purchasePlanId );
}
