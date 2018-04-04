/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.cusmanagement;

import com.serviceapp.bean.systemaudit.MobKeyVal;
import com.serviceapp.mapping.Status;
import java.io.File;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author prathibha_s
 */
public class CustomerMgtInputBean {

    // from bass data tabel
    private String userId;
    private String mobUser;
    private String address;
    private String area;
    private String district;
    private String status;

    // from user table
    private String nic;
    private String gender;
    private String mobile;
    private String firstName;
    private String email;
    private String lastName;

    private File ownImage; // selfie image
    private File prImage; // police report
    private File bcImage; // birth cert
    private File qImage; // qulification img

    private String ownImageContentType;
    private String prImageContentType;
    private String bcImageContentType;
    private String qImageContentType;

    private String ownImageFileName;
    private String prImageFileName;
    private String bcImageFileName;
    private String qImageFileName;

    private byte[] editOwnImage;
    private byte[] editPrImage;
    private byte[] editBcImage;
    private byte[] editQImage;

    private String editOwnImg;
    private String editPrImg;
    private String editBcImg;
    private String editQImg;

    //////////////////////////////////////////////
    private List<Status> statusList;
    private List<MobKeyVal> genderList;


    /*------------------------list data table  ------------------------------*/
    private List<CustomerMgtBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;
    /*------------------------list data table  ------------------------------*/
 /*------------------------for search ------------------------------*/
    private String mobileSearch;
    private String fnameSearch;
    private String lnameSearch;
    private String emailSearch;
    private String nicSearch;
    private String fdate;
    private String tdade;
    private boolean search;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobUser() {
        return mobUser;
    }

    public void setMobUser(String mobUser) {
        this.mobUser = mobUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public File getOwnImage() {
        return ownImage;
    }

    public void setOwnImage(File ownImage) {
        this.ownImage = ownImage;
    }

    public File getPrImage() {
        return prImage;
    }

    public void setPrImage(File prImage) {
        this.prImage = prImage;
    }

    public File getBcImage() {
        return bcImage;
    }

    public void setBcImage(File bcImage) {
        this.bcImage = bcImage;
    }

    public File getqImage() {
        return qImage;
    }

    public void setqImage(File qImage) {
        this.qImage = qImage;
    }

    public String getOwnImageContentType() {
        return ownImageContentType;
    }

    public void setOwnImageContentType(String ownImageContentType) {
        this.ownImageContentType = ownImageContentType;
    }

    public String getPrImageContentType() {
        return prImageContentType;
    }

    public void setPrImageContentType(String prImageContentType) {
        this.prImageContentType = prImageContentType;
    }

    public String getBcImageContentType() {
        return bcImageContentType;
    }

    public void setBcImageContentType(String bcImageContentType) {
        this.bcImageContentType = bcImageContentType;
    }

    public String getqImageContentType() {
        return qImageContentType;
    }

    public void setqImageContentType(String qImageContentType) {
        this.qImageContentType = qImageContentType;
    }

    public String getOwnImageFileName() {
        return ownImageFileName;
    }

    public void setOwnImageFileName(String ownImageFileName) {
        this.ownImageFileName = ownImageFileName;
    }

    public String getPrImageFileName() {
        return prImageFileName;
    }

    public void setPrImageFileName(String prImageFileName) {
        this.prImageFileName = prImageFileName;
    }

    public String getBcImageFileName() {
        return bcImageFileName;
    }

    public void setBcImageFileName(String bcImageFileName) {
        this.bcImageFileName = bcImageFileName;
    }

    public String getqImageFileName() {
        return qImageFileName;
    }

    public void setqImageFileName(String qImageFileName) {
        this.qImageFileName = qImageFileName;
    }

    public byte[] getEditOwnImage() {
        return editOwnImage;
    }

    public void setEditOwnImage(byte[] editOwnImage) {
        this.editOwnImage = editOwnImage;
    }

    public byte[] getEditPrImage() {
        return editPrImage;
    }

    public void setEditPrImage(byte[] editPrImage) {
        this.editPrImage = editPrImage;
    }

    public byte[] getEditBcImage() {
        return editBcImage;
    }

    public void setEditBcImage(byte[] editBcImage) {
        this.editBcImage = editBcImage;
    }

    public byte[] getEditQImage() {
        return editQImage;
    }

    public void setEditQImage(byte[] editQImage) {
        this.editQImage = editQImage;
    }

    public String getEditOwnImg() {
        try {
            byte[] blobAsBytes = getEditOwnImage();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.editOwnImg = new String(blobAsBytes);
        } catch (Exception e) {
            this.editOwnImg = "";
        }
        return editOwnImg;
    }

    public void setEditOwnImg(String editOwnImg) {
        this.editOwnImg = editOwnImg;
    }

    public String getEditPrImg() {
        try {
            byte[] blobAsBytes = getEditPrImage();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.editPrImg = new String(blobAsBytes);
        } catch (Exception e) {
            this.editPrImg = "";
        }
        return editPrImg;
    }

    public void setEditPrImg(String editPrImg) {
        this.editPrImg = editPrImg;
    }

    public String getEditBcImg() {
        try {
            byte[] blobAsBytes = getEditBcImage();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.editBcImg = new String(blobAsBytes);
        } catch (Exception e) {
            this.editBcImg = "";
        }
        return editBcImg;
    }

    public void setEditBcImg(String editBcImg) {
        this.editBcImg = editBcImg;
    }

    public String getEditQImg() {
        try {
            byte[] blobAsBytes = getEditQImage();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.editQImg = new String(blobAsBytes);
        } catch (Exception e) {
            this.editQImg = "";
        }
        return editQImg;
    }

    public void setEditQImg(String editQImg) {
        this.editQImg = editQImg;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<MobKeyVal> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<MobKeyVal> genderList) {
        this.genderList = genderList;
    }

    public List<CustomerMgtBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<CustomerMgtBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public boolean isLoadonce() {
        return loadonce;
    }

    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

    public String getMobileSearch() {
        return mobileSearch;
    }

    public void setMobileSearch(String mobileSearch) {
        this.mobileSearch = mobileSearch;
    }

    public String getFnameSearch() {
        return fnameSearch;
    }

    public void setFnameSearch(String fnameSearch) {
        this.fnameSearch = fnameSearch;
    }

    public String getLnameSearch() {
        return lnameSearch;
    }

    public void setLnameSearch(String lnameSearch) {
        this.lnameSearch = lnameSearch;
    }

    public String getEmailSearch() {
        return emailSearch;
    }

    public void setEmailSearch(String emailSearch) {
        this.emailSearch = emailSearch;
    }

    public String getNicSearch() {
        return nicSearch;
    }

    public void setNicSearch(String nicSearch) {
        this.nicSearch = nicSearch;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getTdade() {
        return tdade;
    }

    public void setTdade(String tdade) {
        this.tdade = tdade;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

}
