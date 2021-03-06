package com.serviceapp.mapping;
// Generated Mar 19, 2018 7:41:02 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Systemuser generated by hbm2java
 */
@Entity
@Table(name="web_systemuser"
    ,catalog="service_app"
)
public class Systemuser  implements java.io.Serializable {


     private String username;
     private String address;
     private String address2;
     private String city;
     private Date createtime;
     private Date dateofbirth;
     private String email;
     private Date expirydate;
     private String fax;
     private String fullname;
     private String initialloginstatus;
     private Date lastupdatedtime;
     private String lastupdateduser;
     private Date loggeddate;
     private String mobile;
     private String nic;
     private String noofinvlidattempt;
     private String password;
     private Status status;
     private String telno;
     private String usertype;

    public Systemuser() {
    }

	
    public Systemuser(String username) {
        this.username = username;
    }
    public Systemuser(String username, String address, String address2, String city, Date createtime, Date dateofbirth, String email, Date expirydate, String fax, String fullname, String initialloginstatus, Date lastupdatedtime, String lastupdateduser, Date loggeddate, String mobile, String nic, String noofinvlidattempt, String password, Status status, String telno, String usertype) {
       this.username = username;
       this.address = address;
       this.address2 = address2;
       this.city = city;
       this.createtime = createtime;
       this.dateofbirth = dateofbirth;
       this.email = email;
       this.expirydate = expirydate;
       this.fax = fax;
       this.fullname = fullname;
       this.initialloginstatus = initialloginstatus;
       this.lastupdatedtime = lastupdatedtime;
       this.lastupdateduser = lastupdateduser;
       this.loggeddate = loggeddate;
       this.mobile = mobile;
       this.nic = nic;
       this.noofinvlidattempt = noofinvlidattempt;
       this.password = password;
       this.status = status;
       this.telno = telno;
       this.usertype = usertype;
    }
   
     @Id 

    
    @Column(name="USERNAME", unique=true, nullable=false, length=64)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="ADDRESS")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="ADDRESS2", length=45)
    public String getAddress2() {
        return this.address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    
    @Column(name="CITY", length=64)
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATETIME", length=19)
    public Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATEOFBIRTH", length=10)
    public Date getDateofbirth() {
        return this.dateofbirth;
    }
    
    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    
    @Column(name="EMAIL")
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="EXPIRYDATE", length=10)
    public Date getExpirydate() {
        return this.expirydate;
    }
    
    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    
    @Column(name="FAX", length=16)
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }

    
    @Column(name="FULLNAME")
    public String getFullname() {
        return this.fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    
    @Column(name="INITIALLOGINSTATUS", length=20)
    public String getInitialloginstatus() {
        return this.initialloginstatus;
    }
    
    public void setInitialloginstatus(String initialloginstatus) {
        this.initialloginstatus = initialloginstatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LASTUPDATEDTIME", length=19)
    public Date getLastupdatedtime() {
        return this.lastupdatedtime;
    }
    
    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    
    @Column(name="LASTUPDATEDUSER", length=64)
    public String getLastupdateduser() {
        return this.lastupdateduser;
    }
    
    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LOGGEDDATE", length=19)
    public Date getLoggeddate() {
        return this.loggeddate;
    }
    
    public void setLoggeddate(Date loggeddate) {
        this.loggeddate = loggeddate;
    }

    
    @Column(name="MOBILE", length=16)
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    @Column(name="NIC", length=12)
    public String getNic() {
        return this.nic;
    }
    
    public void setNic(String nic) {
        this.nic = nic;
    }

    
    @Column(name="NOOFINVLIDATTEMPT", precision=2, scale=0)
    public String getNoofinvlidattempt() {
        return this.noofinvlidattempt;
    }
    
    public void setNoofinvlidattempt(String noofinvlidattempt) {
        this.noofinvlidattempt = noofinvlidattempt;
    }

    
    @Column(name="PASSWORD", length=256)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUSCODE")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    
    @Column(name="TELNO", length=16)
    public String getTelno() {
        return this.telno;
    }
    
    public void setTelno(String telno) {
        this.telno = telno;
    }

    
    @Column(name="USERTYPE", length=10)
    public String getUsertype() {
        return this.usertype;
    }
    
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }




}


