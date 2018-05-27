package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping("item/cat")
@Controller
public class ItemCatController {
	/**
	 * Spring 4 中添加了泛型注入功能，这里通过传入的T自动算出对应的Service类型，比如T=ItemCat ->ItemCatService
	 * ItemCatService 类必须存在，并且已经继承了Service<ItemCat>类。因为这里不是自动代理生成ItemCatService类，只是自动算出了类名，然后到容器中去找这个类
	 * 的实例。
	 * 下面不用这种方式的原因是 这种方式适用与通用类的使用，但是具体业务中各个业务类都有各自的不能公用的方法。所以还得用具体的Service类
	 */
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value="id",defaultValue="0") Long parentId){
		try {
			ItemCat record = new ItemCat();
			record.setParentId(parentId);
			List<ItemCat> list = itemCatService.queryListByWhere(record);
			if(list == null || list.isEmpty()) {
				//资源不存在，返回404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			//资源存在，返回500
			return ResponseEntity.ok(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
