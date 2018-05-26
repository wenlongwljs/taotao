package com.taotao.manage.pojo;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品描述表
 * @author mwlbj
 *
 */
@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo{
	
	/**
	 * 商品ID
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemId;
	
	/**
	 * 商品描述
	 */
	private String itemDesc;
	
	/**
	 * 创建时间
	 */
	private Date created;
	
	/**
	 * 更新时间
	 */
	private Date updated;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long id) {
		this.itemId = id;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
