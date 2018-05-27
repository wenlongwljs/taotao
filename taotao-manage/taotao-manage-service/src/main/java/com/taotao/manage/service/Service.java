package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

public class Service<T extends BasePojo> {
	
	/**
	 * Spring 4 中添加了泛型注入功能，这里通过传入的T自动算出对应的Mapper类型，比如T=ItemCat ->ItemCatMapper
	 * ItemCatMapper 类必须存在，并且已经继承了Mapper<ItemCat>类，因为这里不是自动代理生成ItemCatMapper类，只是自动算出了类名，然后到容器中去找这个类
	 * 的实例。
	 */
	@Autowired
	public Mapper<T> mapper;
	
	/**
	 * 根据id 查询数据
	 * @param id
	 * @return
	 */
	public T queryById(Long id) {
		return this.mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<T> queryAll(){
		return this.mapper.select(null);
	}
	
	/**
	 * 根据条件查询一条记录，如果哟多条记录则抛出异常
	 * @param recod
	 * @return
	 */
	public T queryOne(T record) {
		return this.mapper.selectOne(record);
	}
	
	public List<T> queryListByWhere(T record){
		return this.mapper.select(record);
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
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return this.mapper.insert(record);
	}
	
	/**
	 * 添加数据，添加不为null的字段，返回成功条数
	 * @param record
	 * @return
	 */
	public Integer saveSelective(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return this.mapper.insertSelective(record);
	}
	
	/**
	 * 修改数据，更加主键修改，返回成功条数
	 * @param record
	 * @return
	 */
	public Integer update(T record) {
		record.setUpdated(new Date());
		return this.mapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 修改数据，只修改不为null的字段，返回成功条数据
	 * @param record
	 * @return
	 */
	public Integer updateBySelective(T record) {
		record.setUpdated(new Date());
		return this.mapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 删除数据，更加id删除，返回成功条数
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id) {
		return this.mapper.deleteByPrimaryKey(id);
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
		return this.mapper.deleteByExample(ex);
	}
	
	/**
	 * 删除数据，更加条件删除，返回成功条数
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record) {
		return this.mapper.delete(record);
	}
}
