package com.serviceapp.mapping;
// Generated Mar 19, 2018 7:41:02 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Passwordpolicy generated by hbm2java
 */
@Entity
@Table(name="web_passwordpolicy"
    ,catalog="service_app"
)
public class Passwordpolicy  implements java.io.Serializable {


     private long passwordpolicyid;
     private Date createtime;
     private String description;
     private Long idleaccountexpiryperiod;
     private String initialpasswordexpirystatus;
     private Date lastupdatedtime;
     private String lastupdateduser;
     private Long maximumlength;
     private Long minimumlength;
     private Long minimumlowercasecharacters;
     private Long minimumnumericalcharacters;
     private Long minimumpasswordchangeperiod;
     private Long minimumspecialcharacters;
     private Long minimumuppercasecharacters;
     private Long noofhistorypassword;
     private Long noofinvalidloginattempt;
     private Long passwordexpiryperiod;
     private Long repeatcharactersallow;

    public Passwordpolicy() {
    }

	
    public Passwordpolicy(long passwordpolicyid, Date createtime, Date lastupdatedtime) {
        this.passwordpolicyid = passwordpolicyid;
        this.createtime = createtime;
        this.lastupdatedtime = lastupdatedtime;
    }
    public Passwordpolicy(long passwordpolicyid, Date createtime, String description, Long idleaccountexpiryperiod, String initialpasswordexpirystatus, Date lastupdatedtime, String lastupdateduser, Long maximumlength, Long minimumlength, Long minimumlowercasecharacters, Long minimumnumericalcharacters, Long minimumpasswordchangeperiod, Long minimumspecialcharacters, Long minimumuppercasecharacters, Long noofhistorypassword, Long noofinvalidloginattempt, Long passwordexpiryperiod, Long repeatcharactersallow) {
       this.passwordpolicyid = passwordpolicyid;
       this.createtime = createtime;
       this.description = description;
       this.idleaccountexpiryperiod = idleaccountexpiryperiod;
       this.initialpasswordexpirystatus = initialpasswordexpirystatus;
       this.lastupdatedtime = lastupdatedtime;
       this.lastupdateduser = lastupdateduser;
       this.maximumlength = maximumlength;
       this.minimumlength = minimumlength;
       this.minimumlowercasecharacters = minimumlowercasecharacters;
       this.minimumnumericalcharacters = minimumnumericalcharacters;
       this.minimumpasswordchangeperiod = minimumpasswordchangeperiod;
       this.minimumspecialcharacters = minimumspecialcharacters;
       this.minimumuppercasecharacters = minimumuppercasecharacters;
       this.noofhistorypassword = noofhistorypassword;
       this.noofinvalidloginattempt = noofinvalidloginattempt;
       this.passwordexpiryperiod = passwordexpiryperiod;
       this.repeatcharactersallow = repeatcharactersallow;
    }
   
     @Id 

    
    @Column(name="PASSWORDPOLICYID", unique=true, nullable=false, precision=11, scale=0)
    public long getPasswordpolicyid() {
        return this.passwordpolicyid;
    }
    
    public void setPasswordpolicyid(long passwordpolicyid) {
        this.passwordpolicyid = passwordpolicyid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATETIME", nullable=false, length=19)
    public Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    
    @Column(name="DESCRIPTION", length=64)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="IDLEACCOUNTEXPIRYPERIOD", precision=11, scale=0)
    public Long getIdleaccountexpiryperiod() {
        return this.idleaccountexpiryperiod;
    }
    
    public void setIdleaccountexpiryperiod(Long idleaccountexpiryperiod) {
        this.idleaccountexpiryperiod = idleaccountexpiryperiod;
    }

    
    @Column(name="INITIALPASSWORDEXPIRYSTATUS", length = 10)
    public String getInitialpasswordexpirystatus() {
        return this.initialpasswordexpirystatus;
    }
    
    public void setInitialpasswordexpirystatus(String initialpasswordexpirystatus) {
        this.initialpasswordexpirystatus = initialpasswordexpirystatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LASTUPDATEDTIME", nullable=false, length=19)
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

    
    @Column(name="MAXIMUMLENGTH", precision=11, scale=0)
    public Long getMaximumlength() {
        return this.maximumlength;
    }
    
    public void setMaximumlength(Long maximumlength) {
        this.maximumlength = maximumlength;
    }

    
    @Column(name="MINIMUMLENGTH", precision=11, scale=0)
    public Long getMinimumlength() {
        return this.minimumlength;
    }
    
    public void setMinimumlength(Long minimumlength) {
        this.minimumlength = minimumlength;
    }

    
    @Column(name="MINIMUMLOWERCASECHARACTERS", precision=11, scale=0)
    public Long getMinimumlowercasecharacters() {
        return this.minimumlowercasecharacters;
    }
    
    public void setMinimumlowercasecharacters(Long minimumlowercasecharacters) {
        this.minimumlowercasecharacters = minimumlowercasecharacters;
    }

    
    @Column(name="MINIMUMNUMERICALCHARACTERS", precision=11, scale=0)
    public Long getMinimumnumericalcharacters() {
        return this.minimumnumericalcharacters;
    }
    
    public void setMinimumnumericalcharacters(Long minimumnumericalcharacters) {
        this.minimumnumericalcharacters = minimumnumericalcharacters;
    }

    
    @Column(name="MINIMUMPASSWORDCHANGEPERIOD", precision=11, scale=0)
    public Long getMinimumpasswordchangeperiod() {
        return this.minimumpasswordchangeperiod;
    }
    
    public void setMinimumpasswordchangeperiod(Long minimumpasswordchangeperiod) {
        this.minimumpasswordchangeperiod = minimumpasswordchangeperiod;
    }

    
    @Column(name="MINIMUMSPECIALCHARACTERS", precision=11, scale=0)
    public Long getMinimumspecialcharacters() {
        return this.minimumspecialcharacters;
    }
    
    public void setMinimumspecialcharacters(Long minimumspecialcharacters) {
        this.minimumspecialcharacters = minimumspecialcharacters;
    }

    
    @Column(name="MINIMUMUPPERCASECHARACTERS", precision=11, scale=0)
    public Long getMinimumuppercasecharacters() {
        return this.minimumuppercasecharacters;
    }
    
    public void setMinimumuppercasecharacters(Long minimumuppercasecharacters) {
        this.minimumuppercasecharacters = minimumuppercasecharacters;
    }

    
    @Column(name="NOOFHISTORYPASSWORD", precision=11, scale=0)
    public Long getNoofhistorypassword() {
        return this.noofhistorypassword;
    }
    
    public void setNoofhistorypassword(Long noofhistorypassword) {
        this.noofhistorypassword = noofhistorypassword;
    }

    
    @Column(name="NOOFINVALIDLOGINATTEMPT", precision=11, scale=0)
    public Long getNoofinvalidloginattempt() {
        return this.noofinvalidloginattempt;
    }
    
    public void setNoofinvalidloginattempt(Long noofinvalidloginattempt) {
        this.noofinvalidloginattempt = noofinvalidloginattempt;
    }

    
    @Column(name="PASSWORDEXPIRYPERIOD", precision=11, scale=0)
    public Long getPasswordexpiryperiod() {
        return this.passwordexpiryperiod;
    }
    
    public void setPasswordexpiryperiod(Long passwordexpiryperiod) {
        this.passwordexpiryperiod = passwordexpiryperiod;
    }

    
    @Column(name="REPEATCHARACTERSALLOW", precision=11, scale=0)
    public Long getRepeatcharactersallow() {
        return this.repeatcharactersallow;
    }
    
    public void setRepeatcharactersallow(Long repeatcharactersallow) {
        this.repeatcharactersallow = repeatcharactersallow;
    }




}


