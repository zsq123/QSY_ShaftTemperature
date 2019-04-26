package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 7187628714679791771L;

	public static final String TYPE_MENU = "0";

	public static final String TYPE_BUTTON = "1";

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "MENU_ID")
	private Long id;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "MENU_NAME")
	private String name;

	@Column(name = "URL")
	private String url;

	@Column(name = "PERMS")
	private String perms;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ORDER_NUM")
	private Long orderNum;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_TIME")
	private Date modifyTime;

	@Transient
	private List<Menu> children = new ArrayList<>();

	public List<Menu> getChildren() {

		return children;
	}

	public void setChildren(List<Menu> children) {

		this.children = children;
	}

	/**
	 * @return MENU_ID
	 */
	public Long getId() {

		return id;
	}

	/**
	 * @param menuId
	 */
	public void setId(Long menuId) {

		this.id = menuId;
	}

	/**
	 * @return PARENT_ID
	 */
	public Long getParentId() {

		return parentId;
	}

	/**
	 * @param parentId
	 */
	public void setParentId(Long parentId) {

		this.parentId = parentId;
	}

	/**
	 * @return MENU_NAME
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param menuName
	 */
	public void setName(String name) {

		this.name = name == null ? "" : name.trim();
	}

	/**
	 * @return URL
	 */
	public String getUrl() {

		return url;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {

		this.url = url == null ? "" : url.trim();
	}

	/**
	 * @return PERMS
	 */
	public String getPerms() {

		return perms;
	}

	/**
	 * @param perms
	 */
	public void setPerms(String perms) {

		this.perms = perms == null ? "" : perms.trim();
	}

	/**
	 * @return ICON
	 */
	public String getIcon() {

		return icon;
	}

	/**
	 * @param icon
	 */
	public void setIcon(String icon) {

		this.icon = icon == null ? "" : icon.trim();
	}

	/**
	 * @return TYPE
	 */
	public String getType() {

		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {

		this.type = type == null ? "" : type.trim();
	}

	/**
	 * @return ORDER_NUM
	 */
	public Long getOrderNum() {

		return orderNum;
	}

	/**
	 * @param orderNum
	 */
	public void setOrderNum(Long orderNum) {

		this.orderNum = orderNum;
	}

	/**
	 * @return CREATE_TIME
	 */
	public Date getCreateTime() {

		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	/**
	 * @return MODIFY_TIME
	 */
	public Date getModifyTime() {

		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {

		return "Menu [id=" + id + ", parentId=" + parentId + ", name=" + name + ", url=" + url + ", perms=" + perms + ", icon=" + icon + ", type=" + type + ", orderNum=" + orderNum + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", children=" + children + "]";
	}

}