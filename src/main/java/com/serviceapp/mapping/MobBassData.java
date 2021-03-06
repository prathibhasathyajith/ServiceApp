package com.serviceapp.mapping;
// Generated Apr 4, 2018 10:44:22 AM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * MobBassData generated by hbm2java
 */
@Entity
@Table(name = "mob_bass_data",
        catalog = "service_app"
)
public class MobBassData implements java.io.Serializable {

    private int userId;
    private MobBassLevelConfig mobBassLevelConfig;
    private WebBassQualification webBassQualification;
    private MobUser mobUser;
    private String address;
    private String area;
    private byte[] policeReport;
    private byte[] birthCert;
    private byte[] qualificationImg;
    private String district;
    private Boolean isOnline;
//    private Integer qualificationType;
    private BigDecimal rating;
    private Set<MobServiceRequest> mobServiceRequests = new HashSet(0);
    private Set<MobBassRatings> mobBassRatingses = new HashSet(0);

    public MobBassData() {
    }

    public MobBassData(MobBassLevelConfig mobBassLevelConfig, MobUser mobUser, String address, byte[] policeReport, byte[] birthCert, String district) {
        this.mobBassLevelConfig = mobBassLevelConfig;
        this.mobUser = mobUser;
        this.address = address;
        this.policeReport = policeReport;
        this.birthCert = birthCert;
        this.district = district;
    }

    public MobBassData(MobBassLevelConfig mobBassLevelConfig, MobUser mobUser, WebBassQualification webBassQualification, String address, String area, byte[] policeReport, byte[] birthCert, byte[] qualificationImg, String district, Boolean isOnline,  BigDecimal rating, Set<MobServiceRequest> mobServiceRequests, Set<MobBassRatings> mobBassRatingses) {
        this.mobBassLevelConfig = mobBassLevelConfig;
        this.mobUser = mobUser;
        this.address = address;
        this.area = area;
        this.policeReport = policeReport;
        this.birthCert = birthCert;
        this.qualificationImg = qualificationImg;
        this.district = district;
        this.isOnline = isOnline;
//        this.qualificationType = qualificationType;
        this.rating = rating;
        this.mobServiceRequests = mobServiceRequests;
        this.mobBassRatingses = mobBassRatingses;
        this.webBassQualification = webBassQualification;
    }

    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "mobUser"))
    @Id
    @GeneratedValue(generator = "generator")

    @Column(name = "user_id", unique = true, nullable = false)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level", nullable = false)
    public MobBassLevelConfig getMobBassLevelConfig() {
        return this.mobBassLevelConfig;
    }

    public void setMobBassLevelConfig(MobBassLevelConfig mobBassLevelConfig) {
        this.mobBassLevelConfig = mobBassLevelConfig;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public MobUser getMobUser() {
        return this.mobUser;
    }

    public void setMobUser(MobUser mobUser) {
        this.mobUser = mobUser;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qualification_type")
    public WebBassQualification getWebBassQualification() {
        return this.webBassQualification;
    }

    public void setWebBassQualification(WebBassQualification webBassQualification) {
        this.webBassQualification = webBassQualification;
    }

    @Column(name = "address", nullable = false, length = 1024)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "area", length = 64)
    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "police_report", nullable = false)
    public byte[] getPoliceReport() {
        return this.policeReport;
    }

    public void setPoliceReport(byte[] policeReport) {
        this.policeReport = policeReport;
    }

    @Column(name = "birth_cert", nullable = false)
    public byte[] getBirthCert() {
        return this.birthCert;
    }

    public void setBirthCert(byte[] birthCert) {
        this.birthCert = birthCert;
    }

    @Column(name = "qualification_img")
    public byte[] getQualificationImg() {
        return this.qualificationImg;
    }

    public void setQualificationImg(byte[] qualificationImg) {
        this.qualificationImg = qualificationImg;
    }

    @Column(name = "district", nullable = false, length = 256)
    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "is_online")
    public Boolean getIsOnline() {
        return this.isOnline;
    }
    
//    @Column(name="qualification_type")
//    public Integer getQualificationType() {
//        return this.qualificationType;
//    }
//    
//    public void setQualificationType(Integer qualificationType) {
//        this.qualificationType = qualificationType;
//    }

    
    @Column(name="rating", precision=2, scale=1)
    public BigDecimal getRating() {
        return this.rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mobBassData")
    public Set<MobServiceRequest> getMobServiceRequests() {
        return this.mobServiceRequests;
    }

    public void setMobServiceRequests(Set<MobServiceRequest> mobServiceRequests) {
        this.mobServiceRequests = mobServiceRequests;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mobBassData")
    public Set<MobBassRatings> getMobBassRatingses() {
        return this.mobBassRatingses;
    }

    public void setMobBassRatingses(Set<MobBassRatings> mobBassRatingses) {
        this.mobBassRatingses = mobBassRatingses;
    }

}
