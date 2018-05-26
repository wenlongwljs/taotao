package com.taotao.manage.service;

import java.util.List;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseService<T> {
	
	/**
	 * 定义一个抽象方法，用户继承后实现这个方法，根据不同的操作对象去实现不同的Mapper<T>
	 * @return
	 */
	public abstract Mapper<T> getMapper();
	
	/**
	 * 根据id 查询数据
	 * @param id
	 * @return
	 */
	public T queryById(Long id) {
		return this.getMapper().selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<T> queryAll(){
		return this.getMapper().select(null);
	}
	
	/**
	 * 根据条件查询一条记录，如果哟多条记录则抛出异常
	 * @param recod
	 * @return
	 */
	public T queryOne(T record) {
		return this.getMapper().selectOne(record);
	}
	
	public List<T> queryListByWhere(T record){
		return this.getMapper().select(record);
	}
	
	public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record){
		//设置起始页
		PageHelper.startPage(page,rows);
		//根据条件查询数据
		List<T> list =  this.queryListByWhere(record);
		
		return new PageInfo<T>(list);
	}
	
	/**
	 * 添加数据
	 * @param record
	 * @return
	 */
	public Integer save(T record) {
		return this.getMapper().insert(record);
	}
	
	/**
	 * 添加数据，添加不为null的字段，返回成功条数
	 * @param record
	 * @return
	 */
	public Integer saveSelective(T record) {
		return this.getMapper().insertSelective(record);
	}
	
	/**
	 * 修改数据，更加主键修改，返回成功条数
	 * @param record
	 * @return
	 */
	public Integer update(T record) {
		return this.getMapper().updateByPrimaryKey(record);
	}
	
	/**
	 * 修改数据，只修改不为null的字段，返回成功条数据
	 * @param record
	 * @return
	 */
	public Integer updateBySelective(T record) {
		return this.getMapper().updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 删除数据，更加id删除，返回成功条数
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id) {
		return this.getMapper().deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除数据，返回成功条数
	 * @param clazz
	 * @param property
	 * @param values
	 * @return
	 */
	public Integer deleteByIds(Class<T> clazz,String property, List<Object> values) {
		Example ex = new Example(clazz);
		ex.createCriteria().andIn(property, values);
		return this.getMapper().deleteByExample(ex);
	}
	
	/**
	 * 删除数据，更加条件删除，返回成功条数
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record) {
		return this.getMapper().delete(record);
	}
}
