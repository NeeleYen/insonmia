package com.test.Insomnia.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.ProductPictureDao;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;

@Service
@Transactional
public class ProductPictureService {

//	===Dao===
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductPictureDao pPictureDao;

//	===servlet===
	@Autowired
	private ServletContext context;

	// 抓封面，類型頁用
	public ProductPicture findThumbNail(Integer pId) {
		ProductPicture thumbNail = pPictureDao.findThumbNailByPid(pId);
		Optional<Product> optional = productDao.findById(pId);
		
		// 因為是service不知道誰會用，故還是加驗證
		if (optional.isEmpty()) {
			return null;
		}
		Product product = optional.get();
		
		if (thumbNail == null) {
			ProductPicturePK productPicturePK = new ProductPicturePK(product, 1);
			thumbNail = new ProductPicture(productPicturePK, "/images/logoBrain.jpg");
			return thumbNail;
		}

		// 如果資料庫有資料但在磁碟上找不到資料，就刪除資料庫的資料
		String pathStr = context.getRealPath("") + thumbNail.getFilePath();
		Path path = Paths.get(pathStr).normalize().toAbsolutePath();
		if (!Files.exists(path)) {
			pPictureDao.delete(thumbNail);
			ProductPicturePK productPicturePK = new ProductPicturePK(product, 1);
			thumbNail = new ProductPicture(productPicturePK, "/images/logoBrain.jpg");
		}
		return thumbNail;
	}

	/**
	 * 抓介紹圖片(picNum=2)
	 * 
	 * @param product id (Integer)
	 * @return 圖片Set (picNum=2)
	 */
	public Set<ProductPicture> findIntroPic(Integer pId) {
		Set<ProductPicture> introPic = pPictureDao.findIntroPic(pId);
		return introPic;
	}

	/**
	 * 抓網站全部ProductPicture物件
	 * 
	 * @return List<ProductPicture>
	 */
	public List<ProductPicture> findAllPicOneOfWebsite() {
		List<ProductPicture> allPicOne = pPictureDao.findAllPicOne();
		if (allPicOne == null || allPicOne.isEmpty()) {
			return null;
		}
		return allPicOne;
	}

//	直接以productPicturePK取得物件，才可指定圖片
//	public String getThumbNailPath(Integer pId) {
//		ProductPicturePK pPicturePK = new ProductPicturePK();
//		Optional<Product> optional = productDao.findById(pId);
//		if (optional.isEmpty()) {
//			System.out.println("no product");
//			return null;
//		}
//		Product product = optional.get();
//		pPicturePK.setProductId(product);
//		pPicturePK.setPicNum(1);
//		Optional<ProductPicture> opPic = pPictureDao.findById(pPicturePK);
//		if (optional.isEmpty()) {
//			System.out.println("no product");
//			return null;
//		}
//		ProductPicture pPicture = opPic.get();
//		String filePath = pPicture.getFilePath();
//		return filePath;
//
//	}

	/**
	 * 依id(pk)從sql取得ProductPicture物件
	 * 
	 * @param picturePK
	 * @return ProductPicture
	 */
	public ProductPicture getProductPictureById(ProductPicturePK picturePK) {
		Optional<ProductPicture> optional = pPictureDao.findById(picturePK);
		if (optional.isEmpty()) {
			return null;
		}
		ProductPicture productPicture = optional.get();
		return productPicture;
	}

	public Map<Integer, String> getAllProductPicturePathByProduct(Product product) {
//		map usage refer to
//		https: // www.w3schools.com/java/java_hashmap.asp
//		https: // www.javatpoint.com/java-map
//		https: // www.baeldung.com/java-hashmap
		if (product == null) {
			return null;
		}
		Set<ProductPicture> productPictureSet = product.getProductPicture();
		Map<Integer, String> picNumAndFilePathMap = new HashMap<>();
		for (ProductPicture productPicture : productPictureSet) {
			// 如果資料庫有資料但在磁碟上找不到資料，就刪除資料庫的資料
			String pathStr = context.getRealPath("") + productPicture.getFilePath();
			Path path = Paths.get(pathStr).normalize().toAbsolutePath();
			if (!Files.exists(path)) {
				pPictureDao.delete(productPicture);
			} else {
				// 確定在指定路徑有找到資料才新增至Map
				picNumAndFilePathMap.put(productPicture.getPicId().getPicNum(), productPicture.getFilePath());
			}
		}
		return picNumAndFilePathMap;
//		for ( Integer picNum:picNumAndFilePathMap.keySet() ) {
//			System.out.println("picNum: " + picNum + " - " + "picPath: " + picNumAndFilePathMap.get(picNum));
//		}
	}

	public boolean deleteProductPictureFile(ProductPicture productPicture) {
		String contextFilePathStr = productPicture.getFilePath();

		String realFilePathStr = context.getRealPath("") + contextFilePathStr;
		Path realFilePath = Paths.get(realFilePathStr);
		try {
			if (Files.deleteIfExists(realFilePath)) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 存圖片至磁碟
	 * 
	 * @param photo 傳入檔案
	 * @return 儲存成功會回傳相對專案跟目錄的存檔路徑字串。 儲存失敗會回傳null。
	 */
	public String saveProductPictureFile(MultipartFile photo) {
//		將取得的檔案轉為byte[]
		byte[] productPhotoBytes = null;
		try {
			productPhotoBytes = photo.getBytes();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		檔案重命名避免重複
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-SSS");
		Date date = new Date();
		String fileNameTime = dtf.format(date);
//		存取得的檔案bytes到server disk
//		context.getContextPath() 這是取得專案名稱 
//		String pathStr = "src\\main\\webapp\\image\\" + fileNameTime + photo.getOriginalFilename();
//		ServletContext context.getRealPath("/image/") 是依專案取得真實路徑 !!(重要!)
		String pathStr = context.getRealPath("/image/") + fileNameTime + photo.getOriginalFilename();
		Path path = Paths.get(pathStr).normalize().toAbsolutePath();
//		System.out.println(path.toString());
		try {
			// needed to write file
			Files.write(path, productPhotoBytes);
			return "/image/" + fileNameTime + photo.getOriginalFilename();// 輸入成功，回傳0
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 儲存productPicture至sql
	 * 
	 * @param productPicture
	 */
	public void saveProductPicture(ProductPicture productPicture) {
		pPictureDao.save(productPicture);
	}

	/**
	 * 從sql刪除productPicture
	 * 
	 * @param productPicture
	 */
	public void deleteProductPicture(ProductPicture productPicture) {
		pPictureDao.delete(productPicture);
	}

	// 存圖片Neele wrote
//	public Integer setThumbNailPath(String pathStr, Product product, ProductPicturePK productPicturePK) {
//		Integer status = -1;
//		Optional<ProductPicture> optional = pPictureDao.findById(productPicturePK);
//		if (optional.isEmpty()) {
//			ProductPicture productPicture = new ProductPicture();
//			productPicture product.getP_id()
//			productPicture.setFilePath(pathStr);
//			status = 0;
//		} else {
//			ProductPicture productPicture = optional.get();
//			// 若存在，即覆蓋
//			productPicture.setFilePath(pathStr);
//			status = 1; // 1為覆蓋
//		}
//		return status;
//	}
//	public ProductPicture getThumbnail(Product productId, Integer picNum) {
//	@SuppressWarnings("null")
//	public byte[] getThumbnail(Integer pId) throws IOException {
//		Optional<Product> optional = productDao.findById(pId);
//		if (optional.isEmpty()) {
//			System.out.println("no product");
//			return null;
//		}
//		
//		Product product = optional.get();
//		ProductPicturePK pPicturePK = new ProductPicturePK();
//		pPicturePK.setPicNum(1);
//		pPicturePK.setProductId(product);
//		Optional<ProductPicture> opPic = pPictureDao.findById(pPicturePK);
//		if (optional.isEmpty()) {
//			System.out.println("no picture");
//			return null;
//		}
//		ProductPicture pPicture = opPic.get();
//		String filePath = pPicture.getFilePath();
//		byte[] thumbnailBytes = null;
//		thumbnailBytes = getThumbnailFileBytes(filePath);
//		
//		return thumbnailBytes;
////		File file = new File(filePath);
////		FileOutputStream fos = new FileOutputStream(file);
////		FileReader fr = new FileReader(file);
//		
//		
//	}

//	private byte[] getThumbnailFileBytes(String filePath) throws IOException {
//		Resource resource = resourceLoader.getResource(filePath);
////		File file = resource.getFile();
//		InputStream in = resource.getInputStream();
//		byte[] thumbnailBytes = in.readAllBytes();
//		
//		return thumbnailBytes;
//	}

	public ProductPictureService() {
		super();
	}
}
