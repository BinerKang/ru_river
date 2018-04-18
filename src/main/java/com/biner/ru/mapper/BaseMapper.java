package com.biner.ru.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biner.ru.common.PageResult;



/**
 * 
* @ClassName: BaseMapper 
* @Description: Mybatis基础DAO
* @author xiaoxiang
* @date 2017年12月13日 上午9:38:09
 */
public interface BaseMapper<T> {

	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 */
	T selectById(Serializable id);
	
	/**
	 * 查询单条记录
	 * @param entity
	 * @return
	 */
	T selectOne(@Param("item")T obj);

	/**
	 * 查询记录集合
	 * @param entity
	 * @return
	 */
	List<T> selectList(@Param("item")T obj);
	
	
	/**
	 * 通用的保存方法
	 * @param <T>
	 * @param entity
	 */
	void save(@Param("item")T obj);
	

	/**
	 * 通用的修改方法
	 * @param <T>
	 * @param entity
	 */
	int update(@Param("item")T obj);
	
	
	/**
	 * 批量保存
	 * @param list
	 */
	int batchSave(List<T> list);
	
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int batchUpdate(List<T> list);
	
	/**
	 * 分页查询
	 * @param t
	 * @param page
	 * @return
	 */
	List<T> selectPage(@Param("item")T obj, @Param("page")PageResult<T> page);

	/**
	 * 
	 * @param t
	 * @return
	 */
	int count(@Param("item")T obj);
	
}
