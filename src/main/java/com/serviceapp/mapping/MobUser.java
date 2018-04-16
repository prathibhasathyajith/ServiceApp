package com.serviceapp.mapping;
// Generated Apr 4, 2018 10:44:22 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * MobUser generated by hbm2java
 */
@Entity
@Table(name="mob_user"
    ,catalog="service_app"
    , uniqueConstraints = {@UniqueConstraint(columnNames="mobile"), @UniqueConstraint(columnNames="nic")} 
)
public class MobUser  implements java.io.Serializable {


     private Integer id;
     private Status status;
     private String mobile;
     private String firstName;
     private String email;
     private String lastName;
     private String pin;
     private Boolean isMale;
     private String nic;
     private String salt;
     private Date lastLoginTime;
     private Date createdTime;
     private byte[] image;
     private boolean isBass;
     private Set<UserRoles> userRoleses = new HashSet(0);
     private MobBassData mobBassData;
     private Set<MobSuggestedUser> mobSuggestedUsers = new HashSet(0);
     private Set<MobBassRatings> mobBassRatingses = new HashSet(0);

    public MobUser() {
    }

	
    public MobUser(String mobile, boolean isBass) {
        this.mobile = mobile;
        this.isBass = isBass;
    }
    public MobUser(Status status, String mobile, String firstName, String email, String lastName, String pin, Boolean isMale, String nic, String salt, Date lastLoginTime, Date createdTime, byte[] image, boolean isBass, Set<UserRoles> userRoleses, MobBassData mobBassData, Set<MobSuggestedUser> mobSuggestedUsers,Set<MobBassRatings> mobBassRatingses) {
       this.status = status;
       this.mobile = mobile;
       this.firstName = firstName;
       this.email = email;
       this.lastName = lastName;
       this.pin = pin;
       this.isMale = isMale;
       this.nic = nic;
       this.salt = salt;
       this.lastLoginTime = lastLoginTime;
       this.createdTime = createdTime;
       this.image = image;
       this.isBass = isBass;
       this.userRoleses = userRoleses;
       this.mobBassData = mobBassData;
       this.mobSuggestedUsers = mobSuggestedUsers;
       this.mobBassRatingses = mobBassRatingses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="status")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    
    @Column(name="mobile", unique=true, nullable=false, length=11)
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    @Column(name="first_name", length=64)
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    @Column(name="email", length=256)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="last_name", length=128)
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    @Column(name="pin", length=128)
    public String getPin() {
        return this.pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }

    
    @Column(name="is_male")
    public Boolean getIsMale() {
        return this.isMale;
    }
    
    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }

    
    @Column(name="nic", unique=true, length=11)
    public String getNic() {
        return this.nic;
    }
    
    public void setNic(String nic) {
        this.nic = nic;
    }

    
    @Column(name="salt", length=65535)
    public String getSalt() {
        return this.salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_login_time", length=19)
    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }
    
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_time", length=19)
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    @Column(name="image")
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }

    
    @Column(name="is_bass", nullable=false)
    public boolean isIsBass() {
        return this.isBass;
    }
    
    public void setIsBass(boolean isBass) {
        this.isBass = isBass;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="mobUser")
    public Set<UserRoles> getUserRoleses() {
        return this.userRoleses;
    }
    
    public void setUserRoleses(Set<UserRoles> userRoleses) {
        this.userRoleses = userRoleses;
    }

@OneToOne(fetch=FetchType.EAGER, mappedBy="mobUser")
    public MobBassData getMobBassData() {
        return this.mobBassData;
    }
    
    public void setMobBassData(MobBassData mobBassData) {
        this.mobBassData = mobBassData;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="mobUser")
    public Set<MobSuggestedUser> getMobSuggestedUsers() {
        return this.mobSuggestedUsers;
    }
    
    public void setMobSuggestedUsers(Set<MobSuggestedUser> mobSuggestedUsers) {
        this.mobSuggestedUsers = mobSuggestedUsers;
    }
@OneToMany(fetch=FetchType.EAGER, mappedBy="mobUser")
    public Set<MobBassRatings> getMobBassRatingses() {
        return this.mobBassRatingses;
    }
    
    public void setMobBassRatingses(Set<MobBassRatings> mobBassRatingses) {
        this.mobBassRatingses = mobBassRatingses;
    }



}


