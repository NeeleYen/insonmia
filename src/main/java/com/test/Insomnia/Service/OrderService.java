package com.test.Insomnia.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.OrderDao;
import com.test.Insomnia.model.Order;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.User;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserService userService;
	
	public Order createOrder(Order order) {
		Order save = orderDao.save(order);		
		return save;
	}
	
	public Order findById(Integer id) {
		Optional<Order> findById = orderDao.findById(id);
		if (findById.isEmpty()) {
			return null;
		}
		return findById.get();
	}
	
	public void setOrderCancelStatus(Integer id) {
		orderDao.setOrderCancelStatus(1, id);		
	}
	
	public void setPaymentStatus(Integer id, Integer status) {
		orderDao.setPaymentStatus(status, id);		
	}
	
	//取消訂單時購買方案的訂單數減少
//	public void setPurPlanCurrentOrderAmount(Integer orderId, Integer orderAmount) {
//		Optional<Order> findById = orderDao.findById(orderId);
//		Order order = findById.get();
//		PurchasePlan purchasePlan = order.getPurchasePlan();
//		Integer currentOrderAmount = purchasePlan.getCurrentOrderAmount();
//		Integer purchasePlanId = purchasePlan.getPurchasePlanId();
////		if ((currentOrderAmount-orderAmount)<0) {
////			orderDao.setCurrentOrderAmount(0, purchasePlanId);
////			
////		}
//		orderDao.setCurrentOrderAmount(currentOrderAmount-orderAmount, purchasePlanId);
//	}
	
	public Order findLastestOrder() {
		return orderDao.findFirstByOrderByOrderIdDesc();
	}
	
	public Page<Order> findByPage(Integer pageNum, Integer userId) {//前端傳進來的頁數，會從頁碼 1 開始		
		//(第幾頁開始, 一頁顯示幾筆, 顯示順序, 依哪個屬性排序)
		Pageable of = PageRequest.of(pageNum-1, 5, Sort.Direction.DESC, "orderId");		
		
		User user = userService.findById(userId);
		Page<Order> page = orderDao.findAllByUser(user, of);
		
		return page;
	}
	
	public PurchasePlan findPurPlan(Integer id) {
		Optional<Order> findById = orderDao.findById(id);
		if (findById.isEmpty()) {
			return null;
		}
		Order order = findById.get();
		PurchasePlan purchasePlan = order.getPurchasePlan();
		return purchasePlan;
	}
	
	/**
	 * 依 purchasePlanId 算出 currentOrderAmount 的值 (取代currentOrderAmount欄位)
	 * @param purchasePlanId
	 * @return 
	 * 該purchasePlan的當前已訂購數量(需小於maxOrderAmount)
	 * 若找不到對應purchasePlan或尚無order則回傳null
	 */
	public Integer getCurrentOrderAmountByPurchasePlanId(Integer purchasePlanId) {
		if ( purchasePlanId == null ) {
			return null;
		}
		return orderDao.sumOfOrderedAmountByPurchasePlan(purchasePlanId);
	}
}
