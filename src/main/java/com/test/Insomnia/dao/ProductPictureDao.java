package com.test.Insomnia.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;

public interface ProductPictureDao extends JpaRepository<ProductPicture, ProductPicturePK> {

	@Query(value = "select * from ProductPicture_Table where fk_productId= :fkid "
			+ "and picNum=1", nativeQuery = true)
	public ProductPicture findThumbNailByPid(@Param("fkid") Integer pId);
	
	@Query(value = "select * from ProductPicture_Table where fk_productId= :fkid "
			+ "and picNum=2", nativeQuery = true)
	public Set<ProductPicture> findIntroPic(@Param("fkid") Integer pId);
	
	@Query(value = "select * from ProductPicture_Table where picNum=1", nativeQuery = true)
	public List<ProductPicture> findAllPicOne();
}
