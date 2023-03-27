package com.test.Insomnia.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.AreaDao;
import com.test.Insomnia.model.Area;
import com.test.Insomnia.model.PurchasePlan;

@Service
@Transactional
public class AreaService {

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private PurchasePlanService purchasePlanService;
	
	public Area findById(Integer id) {
		Optional<Area> findById = areaDao.findById(id);
		if (findById.isEmpty()) {
			return null;
		} 
		return findById.get();
	}
	
	public List<Area> listUnusedAreasByPurchasePlanId(Integer purchasePlanId){
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if ( purchasePlan == null ) {
			return null;
		}
		Set<Area> areaSet = purchasePlan.getAreaNo();
		List<Area> allArea = areaDao.findAll();
		allArea.removeAll(areaSet);
		return allArea;
	}
}
