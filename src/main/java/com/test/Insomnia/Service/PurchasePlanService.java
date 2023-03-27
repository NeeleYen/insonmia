package com.test.Insomnia.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.OrderDao;
import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.PurchasePlanDao;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.PurchasePlan;

@Service
@Transactional
public class PurchasePlanService {

	@Autowired
	private PurchasePlanDao pPlanDao;
	@Autowired
	private ProductDao pDao;
	@Autowired
	private OrderDao orderDao;
	
	
	/**
	 * 依productId取回該product下所有purchasePlan(直接使用hibernate的product取出即可?)
	 * @param id
	 * @return
	 */
	public Set<PurchasePlan> getpPlanSet(Integer id) {
		Optional<Product> opProduct = pDao.findById(id);
		Product product = new Product();
		Set<PurchasePlan> purchasePlans = new HashSet<PurchasePlan>(0);
		if (opProduct.isPresent()) {
			product = opProduct.get();
			purchasePlans = product.getPurchasePlan();
		}
		return purchasePlans;
	}
	
	/**
	 * 檢查方案有沒有到上限
	 * @param purchasePlan
	 * @return String (狀態)
	 */
	public String isPurchasePlanToClose(PurchasePlan purchasePlan, Integer orderAmount) {
		Integer currentOrderAmount = orderDao.sumOfOrderedAmountByPurchasePlan(purchasePlan.getPurchasePlanId()) ;
//				purchasePlan.getCurrentOrderAmount();		
		Integer maxOrderAmount = purchasePlan.getMaxOrderAmount();
		if (maxOrderAmount==null || currentOrderAmount==null || maxOrderAmount>(currentOrderAmount+orderAmount)) {
			return "false";	//沒到上限不用關
		}
		if (maxOrderAmount<(currentOrderAmount+orderAmount)) {
			return "true";
		}
		if (maxOrderAmount==(currentOrderAmount+orderAmount)) {
			return "equal";
		}
		return "其他問題";
	}
	
	/**
	 * 依purchasePlanId取得PurchasePlan物件
	 * @param id {@link Integer}
	 * @return {@link PurchasePlan} 
	 */
	public PurchasePlan findById(Integer id) {
		// 修正: 原寫法未考慮指定id不存在之回傳值
		Optional<PurchasePlan> opPurPlan = pPlanDao.findById(id);
		if ( !opPurPlan.isPresent() ) {
			return null;
		}
		return opPurPlan.get();
	}
	
	/**
	 * 將PurchasePlan Entity物件存入資料庫
	 * @param purchasePlan {@link PurchasePlan}
	 * @return {@link PurchasePlan}
	 */
	public PurchasePlan insert( PurchasePlan purchasePlan ) {
		if ( purchasePlan == null ) {
			return null;
		}
		PurchasePlan savedPurchasePlan = pPlanDao.save(purchasePlan);
		return savedPurchasePlan;
	}
	
	/**
	 * 將PurchasePlan Entity物件從資料庫刪除
	 * @param purchasePlan
	 * @return {@link Integer}
	 * 	成功刪除回傳0 刪除失敗(輸入為null)回傳1 其他失敗原因回傳2
	 */
	public Integer delete( PurchasePlan purchasePlan ) {
		// 因此dao.delete為void。不做null判斷故意讓它跳exception給使用的function處理。還是在此處理並回傳錯誤代碼?
		if ( purchasePlan == null ) {
			return 1;
		}
		try {
			pPlanDao.delete(purchasePlan);
		} catch (Exception e) {
			System.out.println( "delete PurchasePlan Error Exception: " + e);
			return 2;
		}
		return 0;
	}
}
