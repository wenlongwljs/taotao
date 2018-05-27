package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{
	/**
	 * 在一个Service中注入另外一个Service。利用事务的传播行为将后一个事务添加到前一个事务中。
	 */
	@Autowired
	private ItemDescService itemDescService;
	
	public void itemSave(Item item, String desc) {
		//初始值为1，上架状态
		item.setStatus(1);
		//处于安全考虑，id由数据库自动生成，避免用户恶意从前端提交id
		item.setId(null);
		super.save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);		
		this.itemDescService.save(itemDesc);
	}

}
