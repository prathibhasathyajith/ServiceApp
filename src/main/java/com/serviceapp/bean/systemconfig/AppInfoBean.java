/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemconfig;

import com.serviceapp.bean.systemaudit.MobKeyVal;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class AppInfoBean {

    private String code;
    private String description;
    private String value;
    private String type;
    private String aboutus;
    private String support;
    private String message;

    private String empty;

    private List<MobKeyVal> typeList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MobKeyVal> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<MobKeyVal> typeList) {
        this.typeList = typeList;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

}
