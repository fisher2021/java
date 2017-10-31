package com.upd.business.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.upd.business.constant.EducationType;
import com.upd.common.basis.entity.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

/**
 * 用户实体
 * Created by Administrator on 2017/5/3.
 */
@Entity
@Table
@DynamicUpdate(true)
public class User extends BaseEntity{
    @Column(length = 20)
    private String account;//账号
    @Column(unique=true,length = 11)
    private String phone;//用户手机号
    @Column(length = 20)
    private String nickname;//用户昵称
    @Column(length = 50)
    private String password;//密码
    @Column(columnDefinition="bit default 0 ",nullable=false)
    private Boolean leader;//是否为领导
    @Transient
    private String token;//身份令牌
    private String image;//头像
    @Column(length = 2)
    private String sex;//性别
    @Column(length = 20)
    private String nativePlace;//籍贯
    @Column(length = 10)
    private String nation;//民族
    @Column(length = 30)
    private String job;//岗位
    @Enumerated(EnumType.ORDINAL)
    private EducationType education;//学历
    @Column(length = 30)
    private String duty;//党内职务
    @DateTimeFormat(pattern="yyyy-MM")
    private Date birth;//出生年月
    @DateTimeFormat(pattern="yyyy-MM")
    private Date partyTime;//入党时间
    @Column(length = 1000)
    private String train;//党内培训记录
    @Column(length = 1000)
    private String award;//奖励记录
    @Column(length = 1000)
    private String punishment;//惩罚记录
    @Column(length = 18)
    private String idCard;//身份证号码
    @Column(length = 100)
    private String address;//现居住地
    @Column(length = 20)
    private String contact;//紧急联系人姓名
    @Column(length = 11)
    private String contactMobile;//紧急联系人电话
    @Column(length = 20)
    private String employeeNumber;//工号
    @Column(columnDefinition="int default 0 ",nullable=false)
    private Integer points;//积分
    @JSONField(serialize=false)
    private Integer groupRank;//党组排序
    @JSONField(serialize=false)
    private Integer committeeRank;//机关党委排序
    @JSONField(serialize=false)
    private Integer branchRank;//党支部排序
    @JSONField(serialize=false)
    @ManyToMany
    @JoinTable(name="org_user",joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="org_id")})
    private List<ORG> orgs = new ArrayList<ORG>();//组织
    @Transient
    private Integer orgLevel;//组织级别
    @Transient
    private Integer orgId;//组织ID,组织架构查看权限
    @JSONField(serialize=false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="highest_org")
    private ORG highestOrg;//最高组织
    @Column(name = "is_deleted")
    private boolean isDeleted;//删除
    @Column(name = "is_difficult",columnDefinition="bit default 0 ")
    private boolean isDifficult;//困难党员

    public boolean isDifficult() {
        return isDifficult;
    }

    public void setDifficult(boolean difficult) {
        isDifficult = difficult;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(Integer groupRank) {
        this.groupRank = groupRank;
    }

    public Integer getCommitteeRank() {
        return committeeRank;
    }

    public void setCommitteeRank(Integer committeeRank) {
        this.committeeRank = committeeRank;
    }

    public Integer getBranchRank() {
        return branchRank;
    }

    public void setBranchRank(Integer branchRank) {
        this.branchRank = branchRank;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public EducationType getEducation() {
        return education;
    }

    public void setEducation(EducationType education) {
        this.education = education;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @JSONField (format="yyyy-MM")
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @JSONField (format="yyyy-MM")
    public Date getPartyTime() {
        return partyTime;
    }

    public void setPartyTime(Date partyTime) {
        this.partyTime = partyTime;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Integer getOrgLevel() {
        if (highestOrg != null){
            return highestOrg.getLevel().ordinal();//返回该用户的最高等级
        }
        return null;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public List<ORG> getOrgs() {
        if (orgs != null && orgs.size() != 0){
            //组织按等级排序
            Collections.sort(orgs, new Comparator<ORG>() {
                @Override
                public int compare(ORG a, ORG b) {
                    return a.getLevel().ordinal() - (b.getLevel().ordinal());
                }
            });
            return orgs;
        }
        return null;
    }

    public void setOrgs(List<ORG> orgs) {
        this.orgs = orgs;
    }

    public ORG getHighestOrg() {
        return highestOrg;
    }

    public void setHighestOrg(ORG highestOrg) {
        this.highestOrg = highestOrg;
    }

    public Integer getOrgId() {
        if(highestOrg != null){
            return highestOrg.getId();
        }
        return null;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}
