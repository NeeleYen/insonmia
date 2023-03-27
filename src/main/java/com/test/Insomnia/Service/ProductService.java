package com.test.Insomnia.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.OrderDao;
import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.User;

@Service
@Transactional
public class ProductService {
	//	---Dao---
	@Autowired
	private ProductDao productDao; 
	@Autowired
	private OrderDao orderDao;
//	@Autowired
//	private SortDefinition sortDefinition;
	
	
	//專案物件
	/**
	 * 依id查找Product物件
	 * @param id
	 * @return Product物件
	 */
	public Product findProductById(Integer id) {
		Optional<Product> optional = productDao.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
	/**
	 * 抓某分類所有專案
	 * @param category id (Integer)
	 * @return 
	 */
	public Set<Product> findAllByCate(Integer category) {
		Set<Product> findAllByCate = productDao.findAllByCate(category);
		return findAllByCate;
	}
	
	/**
	 * 依product id抓募資狀態
	 * @param product id (Integer)
	 * @return 已募得金額 (Integer)
	 */
	public Integer fundationStatus(Integer id) {
		Integer sumOrderPriceByProductId = orderDao.sumOrderPriceByProductId(id, 0);
		if (sumOrderPriceByProductId == null) {
			sumOrderPriceByProductId = 0;
		}
		
		return sumOrderPriceByProductId;
	}
	
	/**
	 * 依product id抓總目標
	 * @param product id (Integer)
	 * @return 目標價格 (Integer)
	 */
	public Integer fundationGoal(Integer productId) {
		Optional<Product> opProduct = productDao.findById(productId);
		Integer goal = 0;
		
		if (opProduct.isEmpty()) {
			return goal;
		}
		
		Product product = opProduct.get();
		Set<PurchasePlan> purchasePlanSet = product.getPurchasePlan();
		for (PurchasePlan purchasePlan : purchasePlanSet) {
			Integer maxOrderAmount = 0;
			if (purchasePlan.getMaxOrderAmount()==null) {
				;
			} else {
				maxOrderAmount = purchasePlan.getMaxOrderAmount();
			}
			Integer price = purchasePlan.getPrice();
			goal += maxOrderAmount*price;
		}
		return goal;
	}
	
	/**
	 * 抓募資百分比
	 * @param status (Integer) 募資狀態
	 * @param goal (Integer) 目標金額
	 * @return String 百分比字串
	 */
	public String fundationPercent(Integer status, Integer goal) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
				
		if (status==null || status==0) {
			return "0";
		} else if (goal==null || goal==0) {
			return "100";
		}
		String percent = numberFormat.format((float)status / (float)goal * 100);		
		return percent;
	}
	
	/**
	 * 加分位符
	 * 參考：https://blog.csdn.net/weixin_30813225/article/details/102410088
	 */
	public String thousandsSeparator(Integer integer) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		String formattedStr = decimalFormat.format(integer);
		return formattedStr;
	}
	
	
	/**
	 * 依product id抓剩餘日期
	 * 參考資料：
	 * https://www.796t.com/p/599610.html
	 * https://www.itread01.com/study/java-date-time.html
	 * @param product id (Integer)
	 * @return 剩餘天數 (Long)
	 */
	public Long getProductTime(Integer id) {
		Long dayDiff = 0L;
		Optional<Product> findById = productDao.findById(id);
		if (findById.isEmpty()) {
			return null;
		}
		Product product = findById.get();
		
		Date closeDate = product.getCloseDate();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = simpleDateFormat.format(date);
		try {
			Date dateToday = simpleDateFormat.parse(today);
			//如果今天大於截止日期設為專案結束，小於時算出剩餘天數
			if (dateToday.after(closeDate)) {
				productDao.setProductStatus(id, 1);	//先設專案狀態 1 為專案結束
				dayDiff = -1L;
			} else {
				long closeTime = closeDate.getTime();
				long todayTime = dateToday.getTime();
				dayDiff = (closeTime - todayTime) / (24 * 60 * 60 * 1000);
			}
		} catch (ParseException e) {
			System.out.println("parse錯誤");
			e.printStackTrace();
		}
		
		return dayDiff;
	}
	
	/**
	 * 檢查該product是否過截止日期
	 * @param Product
	 * @return String status
	 */
//	public String isOutOfDate(Product product) {
//		
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	}
	
	//抓支持人數
	public Integer findSupporterCount(Integer id) {
		Integer findSupportCount = productDao.findSupportCount(id, 0);
		return findSupportCount;
	}
	
	/**
	 * 抓某分類支持人數最多的專案
	 */
	public Product findPopProductByCate(Integer category) {
		Set<Product> maxSupport = new HashSet<Product>(0);
		
		Set<Product> findAllByCate = productDao.findAllByCate(category);
		
		if (findAllByCate==null) {
			return null;
		}
		
//		List<Integer> supportCountList = new ArrayList<Integer>(0);
		Map<Integer, Integer> idAndSupportCount = new HashMap<Integer, Integer>(0);
		for (Product product : findAllByCate) {
			Integer pId = product.getPId();
			Integer supportCount = productDao.findSupportCount(pId, 0);
//			supportCountList.add(supportCount);
			idAndSupportCount.put(pId, supportCount);
		}
//		Optional<Integer> max = supportCountList.stream().max(Integer::compareTo);
		
		if (idAndSupportCount.isEmpty()) {
			return null;
		}
		
		Collection<Integer> collection = idAndSupportCount.values();
		Integer maxSupportCount = Collections.max(collection);
		for (Product product : findAllByCate) {
			Integer pId = product.getPId();
			Integer supportCount = productDao.findSupportCount(pId, 0);
			if (supportCount==maxSupportCount) {
				maxSupport.add(product);
			}
		}
		
		//精選專案中如果有多筆隨機抓一筆
		ArrayList<Product> maxSupportList = new ArrayList<>(maxSupport);
		int randomIndex = new Random().nextInt(maxSupportList.size());
		Product product = maxSupportList.get(randomIndex);
		
		
		return product;
		
	}
	
	/**
	 * 抓整個網站支持人數最多的product
	 * @return Product
	 */
	public Product mostPopOfWebsite() {
		
		List<Product> findAll = productDao.findAll(); // 量大效能差，可改用SQL語句抓
		if ( findAll == null || findAll.isEmpty() ) {
			return null;
		}
		Map<Integer, Integer> pIdAndSupportAmount = new HashMap<>();
		for (Product product : findAll) {
			Integer pId = product.getPId();
			Integer findSupportCount = productDao.findSupportCount(pId, 0);
			pIdAndSupportAmount.put(pId, findSupportCount);
		}
		Collection<Integer> supportCount = pIdAndSupportAmount.values();
		Integer max = Collections.max(supportCount);
		Set<Product> maxSupport = new HashSet<>();
		for (Product product : findAll) {
			Integer pId = product.getPId();
			Integer findSupportCount = productDao.findSupportCount(pId, 0);
			if (findSupportCount==max) {
				maxSupport.add(product);
//				break;
			}
		}
		
		//精選專案中如果有多筆隨機抓一筆
		ArrayList<Product> maxSupportList = new ArrayList<>(maxSupport);
		int randomIndex = new Random().nextInt(maxSupportList.size());
		Product product = maxSupportList.get(randomIndex);
		if (product == null) {
			return null;
		}
		return product;			
	}
	
	/**
	 * 抓某分類ID最大的三個product(最新專案)*/
	public Set<Product> findThreeNewest(Integer category) {
				
		Set<Product> treeLastest = productDao.findTreeLastest(category);
		return treeLastest;
	}
	
	
	// 產品首頁圖
	public Product save(Product product) {
//		產生prototype bean (不使用new)?\
		String intro = product.getIntro().trim();
		String title = product.getTitle().trim();
		if ( !( intro.isBlank() || title.isBlank() ) ) {
			product.setIntro(intro);
			product.setTitle(title);
			return productDao.save(product); 
		}
		return null;
	}
	
	
	/**
	 * 依user讀取product ajax
	 * @param user User
	 * @param pageNumber Integer
	 * @return Page<Product>
	 */
	public Page<Product> findProductByUserOrderByPage(User user, Integer pageNumber){
//		轉換List為page物件
//		https://stackoverflow.com/questions/37749559/conversion-of-list-to-page-in-spring
		
//		切頁工具
		Pageable pgb = PageRequest.of(pageNumber-1, 5, Sort.Direction.DESC, "closeDate"); // TODO 排序未解決
//		select該user的所有專案
		List<Product> products = productDao.findByUserId(user.getId());
//		轉換專案List為page物件
//		PagedListHolder<Product> pagedListHolder = new PagedListHolder<>(products);
		int start = (int)pgb.getOffset();
		final int end = Math.min((start + pgb.getPageSize()), products.size());
		final Page<Product> ProductsPage = new PageImpl<>(products.subList(start, end), pgb, products.size());
		
		return ProductsPage;
	}
	
	public List<Product> findActiveProductRandomly(){
		return productDao.findActiveProductRandomly();
	}
}
