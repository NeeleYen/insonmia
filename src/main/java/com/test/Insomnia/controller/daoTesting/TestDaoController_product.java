package com.test.Insomnia.controller.daoTesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;

@Controller
public class TestDaoController_product {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;

	// @Value("classpath:image/testImage.jpg")
	// private Resource resource;
	// @Autowired
	// private ResourceLoader resourceLoader;

	@GetMapping("/addProduct")
	public String addProduct() {
		// Area taiwan = new Area();
		String filePath = "/image/testImage.jpg";
		Product popokemonGo = new Product();
		Set<ProductPicture> pPictureSet = new HashSet<ProductPicture>(0);
		ProductPicture pPicture = new ProductPicture();
		ProductPicturePK pPicturePK = new ProductPicturePK();
		
		Item pokemon = new Item();
		Item pokemon2 = new Item();
		Item pokemon3 = new Item();
		Set<Item> pokemons = new HashSet<Item>(0);
		
		// taiwan.setArea("taiwan");
		popokemonGo.setCategory(1);
//		popokemonGo.setFaq("Q:販售捕捉到的神奇寶貝的款項多久會匯到帳戶? A:大約2~3個工作天。");
		popokemonGo.setIntro("這是一款神奇寶貝的AR遊戲，快來一起捕捉神奇寶貝吧。");
		popokemonGo.setTitle("popokemonGo手遊");
		Date testDate = null;
		try {
			testDate = new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/27");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		popokemonGo.setCloseDate(testDate);
		pPicturePK.setPicNum(1); // 預設1為專案封面
		pPicturePK.setProductId(popokemonGo);
		pPicture.setFilePath(filePath);
		pPicture.setPicId(pPicturePK);
		pPictureSet.add(pPicture);
		popokemonGo.setProductPicture(pPictureSet);
		
		pokemon.setItemName("小火龍");
		pokemon2.setItemName("妙蛙種子");
		pokemon3.setItemName("傑尼龜");
		pokemon.setProductId(popokemonGo);
		pokemon2.setProductId(popokemonGo);
		pokemon3.setProductId(popokemonGo);
		pokemons.add(pokemon);
		pokemons.add(pokemon2);
		pokemons.add(pokemon3);
		popokemonGo.setItem(pokemons);
		
		// popokemonGo.setCloseDate()
		// popokemonGo.setP_id(1);
		// popokemonGo.set(1); // 沒有getter setter無法設定fk欄位值
		Optional<User> userQuery = userDao.findById(1);
		if (userQuery.isPresent()) {
			User user1 = userQuery.get();
			popokemonGo.setUser(user1);
			productDao.save(popokemonGo);
		}
		// areaDao.save(taiwan);
		// productDao.save(popokemonGo);

		return "redirect:/";
	}

	// 產品首頁圖
	// private byte[] getThumbnailFileBytes() throws IOException {
	// File file = resource.getFile();
	// InputStream in = resource.getInputStream();
	// byte[] thumbnailBytes = in.readAllBytes();
	// return thumbnailBytes;
	// }

}