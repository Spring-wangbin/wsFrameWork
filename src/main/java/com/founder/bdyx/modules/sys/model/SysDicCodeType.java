package com.founder.bdyx.modules.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.founder.bdyx.core.entity.BaseEntity;

@Table(name = "f_sys_dic_code_type")
public class SysDicCodeType extends BaseEntity{
    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable=false)
    private Integer id;

    /**
     * 编码
     */
    @Column(name = "TYPE_CODE")
    private String typeCode;

    /**
     * 名称
     */
    @Column(name = "TYPE_NAME")
    private String typeName;

    /**
     * 是否激活
     */
    @Column(name = "IS_ACTIVE")
    private String isActive;

    /**
     * 备注
     */
    @Column(name = "TYPE_MEMO")
    private String typeMemo;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取编码
     *
     * @return TYPE_CODE - 编码
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置编码
     *
     * @param typeCode 编码
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取名称
     *
     * @return TYPE_NAME - 名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置名称
     *
     * @param typeName 名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取是否激活
     *
     * @return IS_ACTIVE - 是否激活
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * 设置是否激活
     *
     * @param isActive 是否激活
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * 获取备注
     *
     * @return TYPE_MEMO - 备注
     */
    public String getTypeMemo() {
        return typeMemo;
    }

    /**
     * 设置备注
     *
     * @param typeMemo 备注
     */
    public void setTypeMemo(String typeMemo) {
        this.typeMemo = typeMemo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}