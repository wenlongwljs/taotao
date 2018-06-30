package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemParam;

@Service
public class ItemParamService extends BaseService<ItemParam>{
	
	@Autowired
	private ItemParamMapper itemParamMapper;
	
	public PageInfo<ItemParam> queryItemParamList(Integer page, Integer rows) {
		
		Example example = new Example(ItemParam.class);
				example.setOrderByClause("updated desc");
		PageHelper.startPage(page,rows);
		List<ItemParam> itemParamList = this.itemParamMapper.selectByExample(example);
		return new PageInfo<ItemParam>(itemParamList);
	}

}
