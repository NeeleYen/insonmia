package com.test.Insomnia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;

public interface PurchasePlanItemsDao extends JpaRepository<PurchasePlanItems, PurchasePlanItemsPK> {

	// refer: https://docs.jboss.org/hibernate/stable/core.old/reference/en/html/queryhql-subqueries.html
	@Query(value = "from Item as items"
			+ "	where items.itemId not in (select planItems.purchasePlanItemsPK.itemIdPK from PurchasePlanItems as planItems where planItems.purchasePlanItemsPK.purchasePlanIdPK.purchasePlanId = :purchasePlanId) "+
			" and items.productId.PId = :productId")
	public List<Item> listUnusedItemsByPurchasePlanId(@Param("purchasePlanId") Integer purchasePlanId, @Param("productId") Integer productId);
	
}
