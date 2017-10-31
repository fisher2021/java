package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.common.basis.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**文件地址存储实体类
 * Created by ljw on 2017/5/8.
 */
@MappedSuperclass
public class File extends BaseEntity {
    @JSONField(serialize = false)
    private String path;//存储路径
    private String url;//访问路径
//    @JSONField(serialize = false)
//    private Integer type;//文件类型 1:资料
//    private String title;//标题
//    @JSONField(serialize = false)
//    @Column(name = "org_id")
//    private Integer orgId;//管理员组织ID
//    @JSONField(serialize = false)
//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JoinTable(name="file_user",joinColumns={@JoinColumn(name="file_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
//    private List<User> users = new ArrayList<>();

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public Integer getOrgId() {
//        return orgId;
//    }
//
//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
//
//    public void setOrgId(Integer orgId) {
//        this.orgId = orgId;
//    }

//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
