package com.test.Insomnia.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Insomnia.dao.ItemDao;
import com.test.Insomnia.model.Item;

@Service
@Transactional
public class ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	public Item findById( Integer id ) {
		Optional<Item> option = itemDao.findById(id);
		if ( option.isEmpty() ) {
			return null;
		}
		Item item = option.get();
		return item;
	}
	
	public Item insert(Item item) {
		return itemDao.save(item);
	}
	
	public void delete(Item item) {
		itemDao.delete(item);
	}
	
	public ItemService() {
		super();
		// 預設建構子
	}
	
	
}
