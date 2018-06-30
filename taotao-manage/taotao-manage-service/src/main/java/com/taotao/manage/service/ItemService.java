package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>{
	/**
	 * 在一个Service中注入另外一个Service。利用事务的传播行为将后一个事务添加到前一个事务中。
	 */
	@Autowired
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@Autowired
	private ItemMapper itemMapper;
	
	public Boolean saveItem(Item item, String desc,String itemParams) {
		//初始值为1，上架状态
		item.setStatus(1);
		//处于安全考虑，id由数据库自动生成，避免用户恶意从前端提交id
		item.setId(null);
		Integer count1 = super.save(item);
		//保存描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer count2 = this.itemDescService.save(itemDesc);
		//保存类目模板信息
		ItemParamItem itemParamItem = new ItemParamItem();
			itemParamItem.setItemId(item.getId());
			itemParamItem.setParamData(itemParams);
		Integer count3 = itemParamItemService.save(itemParamItem);
		return count1.intValue() == 1 && count2.intValue() == 1 && count3.intValue() == 1;
	}

	public PageInfo<Item> queryItemList(Integer page, Integer rows) {
		Example example = new Example(Item.class);
		example.setOrderByClause("updated desc");
		//设置分页参数
		PageHelper.startPage(page,rows);
		List<Item> list = this.itemMapper.selectByExample(example);
		return new PageInfo<Item>(list);
	}

	public Boolean updateItem(Item item, String desc) {
		//处于安全考虑，将不可以修改的字段设置为null,避免用户恶意从前端提交
		item.setCreated(null);
		item.setStatus(null);
		Integer count1 = super.updateBySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setCreated(null);
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer count2 = this.itemDescService.updateBySelective(itemDesc);
		
		return count1.intValue() == 1 && count2.intValue() == 1;
	}

}
