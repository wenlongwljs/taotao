package com.taotao.manage.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_item")
public class Item extends BasePojo{
	  
	/**
	 * 商品id，同时也是商品编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 商品卖点
	 */
	private String sellPoint;
	
	/**
	 * 商品价格，单位为：分
	 */
	private Long price;
	
	/**
	 * 库存数量
	 */
	private Integer num;
	
	/**
	 * 商品条形码
	 */
	private String barcode;
	
	/**
	 * 商品图片
	 */
	private String image;
	
	/**
	 * 所属类目，叶子类目
	 */
	private Long cid;
	
	/**
	 * 商品状态，1-正常，2-下架，3-删除
	 */
	private Integer status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
