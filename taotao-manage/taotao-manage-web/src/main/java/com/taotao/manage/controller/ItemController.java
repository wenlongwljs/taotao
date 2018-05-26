package com.taotao.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc){
		
		try {
			if(StringUtils.isEmpty(item.getTitle())) {
				//参数有误 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			//初始值为1
			item.setStatus(1);
			//处于安全考虑，id有数据库自动生成
			item.setId(null);
			this.itemService.save(item);
			
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			this.itemDescService.save(itemDesc);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
