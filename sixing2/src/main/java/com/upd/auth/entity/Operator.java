package com.upd.auth.entity;

import com.upd.business.entity.ORG;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 管理员实体类
 * @author cao.xin
 * 2016年12月29日
 */
@Entity
@DynamicUpdate
public class Operator extends BaseEntity {

	@Column(unique=true,length = 20)
	private String account;//账号
	@Column(length = 20)
	private String name;//姓名
	@Column(length = 50)
	private String password;//密码
	@ManyToOne
	private Role role;//管理员所属角色
    @ManyToOne
    private ORG org;//所属组织
	@Column
	private int deleted;//是否已删除 0为正常，1为已删除

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

    public ORG getOrg() {
        return org;
    }

    public void setOrg(ORG org) {
        this.org = org;
    }

	public int isDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
