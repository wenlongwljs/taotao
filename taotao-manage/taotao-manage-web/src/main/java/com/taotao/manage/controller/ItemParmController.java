package com.taotao.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

/**
 * 商品规格参数模板
 * @author mwlbj
 *
 */
@Controller
@RequestMapping("item/param")
public class ItemParmController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemParmController.class);
	
	@Autowired
	private ItemParamService itemParamService;
	
	/**
	 * 根据类目ID查询对应的商品规格参数模板
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}" , method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId){
		try {
			ItemParam record = new ItemParam();
			record.setItemCatId(itemCatId);
			ItemParam itemParam = this.itemParamService.queryOne(record);
			if(itemParam == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemParam);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(ItemParam itemParam,@PathVariable("itemCatId") Long itemCatId){
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("新增类目参数模板-ItemParam{} ，itemCatId:{}", itemParam, itemCatId);
		}
		
		try {
			itemParam.setItemCatId(itemCatId);
			Integer flag = this.itemParamService.save(itemParam);
			if(flag == 1) {
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(LOGGER.isDebugEnabled()) {
				LOGGER.info("新增类目参数模板-ItemParam{}", itemParam, itemCatId);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemParamList(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows){
		try {
			PageInfo<ItemParam> pageInfo = this.itemParamService.queryItemParamList(page,rows);
			EasyUIResult result = new EasyUIResult(pageInfo.getSize(), pageInfo.getList());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
