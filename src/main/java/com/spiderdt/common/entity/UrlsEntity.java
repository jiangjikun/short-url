package com.spiderdt.common.entity;

import java.io.Serializable;

/**
 * Created by fivebit on 2017/7/12.
 */
public class UrlsEntity implements Serializable {

    private static final long serialVersionUID = -8039686696076337054L;
    private Integer id;
    private String code;
    private String orgUrl;
    private String createTime;
    private String updateTime;
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UrlsEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", orgUrl='" + orgUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
