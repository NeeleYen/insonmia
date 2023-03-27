package com.test.Insomnia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Insomnia.model.Area;

// JpaRepository<Entity名字, Entity id 型別> 
public interface AreaDao extends JpaRepository<Area, Integer> {

	// refer: https://docs.jboss.org/hibernate/stable/core.old/reference/en/html/queryhql-subqueries.html
//	@Query(value = "select * from Area_Table where areaId not in (select FK_areaId from shippingArea where FK_purchasePlanId = :purchasePlanId)", 
//	nativeQuery = true)
//@Query(value = "from Area as areas"
//+ "	where areas.areaId not in (select plan.areaNo.areaId from PurchasePlan as plan where plan.purchasePlanId = :purchasePlanId)")
//+ "	where areas.areaId not in (select PurchasePlan.areaNo.areaId from PurchasePlan where PurchasePlan.purchasePlanId = :purchasePlanId)")
//public List<Area> listUnusedAreasByPurchasePlanId(@Param("purchasePlanId") Integer purchasePlanId);
}
