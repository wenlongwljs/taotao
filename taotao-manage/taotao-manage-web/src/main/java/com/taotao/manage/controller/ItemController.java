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
import com.taotao.manage.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
	
	/**
	 * Spring 4 中添加了泛型注入功能，这里通过传入的T自动算出对应的Service类型，比如T=ItemCat ->ItemCatService
	 * ItemCatService 类必须存在，并且已经继承了Service<ItemCat>类。因为这里不是自动代理生成ItemCatService类，只是自动算出了类名，然后到容器中去找这个类
	 * 的实例。
	 * 下面不用这种方式的原因是 这种方式适用与通用类的使用，但是具体业务中各个业务类都有各自的不能公用的方法。所以还得用具体的Service类
	 */
//	@Autowired
//	private Service<Item> itemService;
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc){
		try {
			if(StringUtils.isEmpty(item.getTitle())) {
				//参数有误 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			//将Item 和 ItemDesc的保存放到一个Service方法中进行，这样就保持了事务的一致性。
			this.itemService.itemSave(item,desc);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
