package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat>{
	
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public Mapper<ItemCat> getMapper() {
		// TODO Auto-generated method stub
		return this.itemCatMapper;
	}
	
//	/**
//	 * 根据父节点ID查询商品类目列表
//	 * @param parentId
//	 * @return
//	 */
//	public List<ItemCat> queryItemCatListByParntId(Long parentId){
//		ItemCat recod = new ItemCat();
//		recod.setParentId(parentId);
//		return this.itemCatMapper.select(recod);
//	}
	
	

}
