package com.test.Insomnia.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.PurchasePlanItemsDao;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;

@Service
@Transactional
public class PurchasePlanItemsService {

	@Autowired
	private PurchasePlanItemsDao pPlanItemsDao;
	@Autowired
	private PurchasePlanService purchasePlanService;
	
//	public Set<PurchasePlanItems> getPurPlanItemsSet(Set<PurchasePlan> pPlan) {
//		
//	}
	
	public HashMap<String, String> findItemNameAmount(Set<PurchasePlanItems> purchasePlanItems) {
		HashMap<String, String> hashMap = new HashMap<String, String>(0);		
		
		for (PurchasePlanItems purPlanItem : purchasePlanItems) {
			PurchasePlanItemsPK purchasePlanItemsPK = purPlanItem.getPurchasePlanItemsPK();
			Item itemIdPK = purchasePlanItemsPK.getItemIdPK();
			String itemName = itemIdPK.getItemName();
			String amount = "" + purPlanItem.getAmount();
			hashMap.put(itemName, amount);
		}
		
		return hashMap;
	}
	
	/**
	 * 回傳購買項目貨品清單的完整字串Set【不用#隔開】
	 * @param Map<String, String> 貨品名稱，數量
	 * @return String
	 */
	public Set<String> getItemNameAmountStr(Map<String, String> map) {
		
		String[] array = map.keySet().toArray(new String[map.size()]);
		String itemAndAmount = "";
		Set<String> strNASet = new HashSet<String>(0);
		
		for (int i = 0; i < map.size(); i++) {			
			itemAndAmount = array[i] + "：" + map.get(array[i]) + "元";
			strNASet.add(itemAndAmount);
		}
		return strNASet;
	}
	
	/**
	 * 普通的insert
	 * @param purchasePlanItems
	 * @return 輸入成功會the saved entity; will never be null. 失敗會return null。
	 */
	public PurchasePlanItems insert(PurchasePlanItems purchasePlanItems) {
		PurchasePlanItems savedPurchasePlanItems = null;
		try {
			savedPurchasePlanItems = pPlanItemsDao.save(purchasePlanItems);
		} catch (IllegalArgumentException e) {
			System.out.println("input is null: " + e);
			return null;
		} catch (Exception e) {
			System.out.println("other exception: " + e);
			return null;
		}
		return savedPurchasePlanItems;
	}
	
	public PurchasePlanItems findById(PurchasePlanItemsPK purchasePlanItemsPK) {
		if ( purchasePlanItemsPK == null ) {
			return null;
		}
		Optional<PurchasePlanItems> option = pPlanItemsDao.findById(purchasePlanItemsPK);
		if ( option.isEmpty() ) {
			return null;
		}
		return option.get();
	}
	
	/**
	 * 依PurchasePlanItems entity刪除資料庫指定的資料列
	 * @param purchasePlanItems
	 * @return 成功回傳true，失敗回傳false
	 */
	public boolean deleteByEntity( PurchasePlanItems purchasePlanItems ) {
		try {
			pPlanItemsDao.delete(purchasePlanItems);
		} catch (IllegalArgumentException e) {
			System.out.println("input is null: " + e);
			return false;
		} catch (Exception e) {
			System.out.println("Other exception: " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * 依purchasePlanId列出該product下所有尚未被指定purchasePlan使用的所有item
	 * @param purchasePlanId
	 * @return {@link List} itemList
	 */
	public List<Item> listUnusedItemsByPurchasePlanId(Integer purchasePlanId) {
		if ( purchasePlanId == null ) {
			return null;
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if ( purchasePlan == null ) {
			return null;
		}
		List<Item> unusedItemsList = pPlanItemsDao.listUnusedItemsByPurchasePlanId(purchasePlanId, purchasePlan.getProduct().getPId());
		if ( unusedItemsList.isEmpty() ) {
			return null;
		}
		return unusedItemsList;
	}
}
