package com.infogen.example.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author larry/larrylv@outlook.com/创建时间 2015年5月8日 上午11:40:39
 * @since 1.0
 * @version 1.0
 */
@Entity
@Table(name = "infogen_demo_user")
public class InfoGen_Demo_User implements Serializable {
	private static final long serialVersionUID = -5520141257917073780L;

	@Id
	@Column(name = "id", length = 32, nullable = false)
	private String id = UUID.randomUUID().toString().replace("-", "");

	@Column(length = 45, unique = true, nullable = false)
	private String account;// 机构帐号 ex:juxinli
	@Column(length = 64, unique = false, nullable = false)
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
