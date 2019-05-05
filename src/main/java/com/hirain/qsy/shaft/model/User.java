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

@Table(name = "t_user")
public class User implements Serializable {

	private static final long serialVersionUID = -4852732617765810959L;

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	private String name;

	private String idNum;

	private String deptName;

	private Long parentId;

	private String parentName;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Transient
	private List<Role> roleList = new ArrayList<>();

	/**
	 * @return USER_ID
	 */
	public Long getId() {

		return id;
	}

	/**
	 * @param userId
	 */
	public void setId(Long id) {

		this.id = id;
	}

	/**
	 * @return USERNAME
	 */
	public String getUsername() {

		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {

		this.username = username == null ? null : username.trim();
	}

	/**
	 * @return PASSWORD
	 */
	public String getPassword() {

		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {

		this.password = password == null ? null : password.trim();
	}

	/**
	 * @return CRATE_TIME
	 */
	public Date getCreateTime() {

		return createTime;
	}

	/**
	 * @param crateTime
	 */
	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public List<Role> getRoleList() {

		return roleList;
	}

	public void setRoleList(List<Role> roleList) {

		this.roleList = roleList;
	}

	@Override
	public String toString() {

		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", idNum=" + idNum + ", deptName="
				+ deptName + ", parentId=" + parentId + ", parentName=" + parentName + ", createTime=" + createTime + ", roleList=" + roleList + "]";
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getIdNum() {

		return idNum;
	}

	public void setIdNum(String idNum) {

		this.idNum = idNum;
	}

	public String getDeptName() {

		return deptName;
	}

	public void setDeptName(String deptName) {

		this.deptName = deptName;
	}

	public Long getParentId() {

		return parentId;
	}

	public void setParentId(Long parentId) {

		this.parentId = parentId;
	}

	public String getParentName() {

		return parentName;
	}

	public void setParentName(String parentName) {

		this.parentName = parentName;
	}

}