package com.test.Insomnia.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;

public interface ProductDao extends JpaRepository<Product, Integer> {
	// public interface ProductDao extends JpaRepository<Entity, pk_datatype> {
	
	// 不顯示已刪除專案
	@Query(value = "from Product where FK_UserId = ?1 and status != -1")
	public List<Product> findByUserId(Integer id);
	
	//抓某product的支持人數
	@Query(value = "select count(distinct Order_Table.fK_UserId) "
			+ "from PurchasePlan_Table "
			+ "inner join Order_Table "
			+ "on PurchasePlan_Table.purchasePlanId=Order_Table.fk_PurchasePlan "
			+ "where PurchasePlan_Table.FK_ProductId = :pid and Order_Table.cancelStatus= :c", nativeQuery = true)
	public Integer findSupportCount(@Param("pid") Integer pid,@Param("c") Integer cancelStatus);
	
	@Modifying
	@Query(value = "update Product p set p.status = :ps where p.PId = :id")
	public void setProductStatus(@Param("ps") Integer status, @Param("id") Integer id);

	// 顯示所有專案(含已刪除專案)
	@Query(value = "from Product where FK_UserId = ?1")
	public List<Product> findAllByUserId(Integer id);
	
	//抓某類型的所有product	 
	@Query(value = "from Product p where p.category = :pc")
	public Set<Product> findAllByCate(@Param("pc") Integer category);
	
	//抓某類型ID最大的三個product
	@Query(value = "select top (3) * from Product_Table pt "
			+ "where pt.category = :c order by pt.PId desc", 
			nativeQuery = true)
	public Set<Product> findTreeLastest(@Param("c") Integer category);
	
	@Query(value = "SELECT TOP 5 * FROM Product_Table where status != -1 ORDER BY NEWID() ", nativeQuery = true)
	public List<Product> findActiveProductRandomly();
	
	//抓輸入的product集合中支持人數最多的
//	@Query(value = "")
	
	// @Query(value="from Customer where name = :n and level = :lev")
	// public Customer findCustomerByNameAndLevel(@Param("n") String
	// name,@Param("lev") Integer level);
	//
	// @Query(value = "from Customer where level = :lev order by id desc")
	// public List<Customer> findByLevelOrderById(@Param("lev") Integer level);
	//
	// @Query(value="select * from customer where name = :n", nativeQuery = true)
	// public Customer findCustomerByNameNativeQuery(@Param("n") String name);

	// @Transactional
	// @Modifying
	// @Query(value="delete from customer where id = ?1 and name = ?2", nativeQuery
	// = true)
	// public void deleteCustomerByIdAndName(Integer id, String name);
	//
	// public List<Customer> findByNameContaining(String str);
}
