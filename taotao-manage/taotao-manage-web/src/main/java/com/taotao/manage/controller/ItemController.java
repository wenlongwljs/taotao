package com.taotao.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	
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
	public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams){
		try {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("新增商品，item = {} , desc = {}" ,item ,desc);
			}
			
			if(StringUtils.isEmpty(item.getTitle())) {
				//参数有误 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			//将Item 和 ItemDesc的保存放到一个Service方法中进行，这样就保持了事务的一致性。
			Boolean flag = this.itemService.saveItem(item,desc,itemParams);
			
			if(!flag) {
				if(LOGGER.isInfoEnabled()) {
					LOGGER.info("新增商品失败，item = {} , desc = {}" ,item ,desc);
				}
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("新增商品成功 itemId = {}" , item.getId());
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("新增商品失败，item = {} , desc = {}" ,item ,desc , e);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * 查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(
				@RequestParam(value="page" ,defaultValue="1") Integer page,
				@RequestParam(value="rows" ,defaultValue="30") Integer rows){
		try {
			PageInfo<Item> pageInfo = this.itemService.queryItemList(page, rows);
			EasyUIResult result = new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
			return ResponseEntity.ok(result);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc){
		try {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("修改商品，item = {} , desc = {}" ,item ,desc);
			}
			
			if(StringUtils.isEmpty(item.getTitle())) {
				//参数有误 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			//将Item 和 ItemDesc的保存放到一个Service方法中进行，这样就保持了事务的一致性。
			Boolean flag = this.itemService.updateItem(item,desc);
			
			if(!flag) {
				if(LOGGER.isInfoEnabled()) {
					LOGGER.info("修改商品失败，item = {} , desc = {}" ,item ,desc);
				}
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("修改商品成功 itemId = {}" , item.getId());
			}
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("修改商品失败，item = {} , desc = {}" ,item ,desc , e);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
 