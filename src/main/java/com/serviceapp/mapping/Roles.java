package com.serviceapp.mapping;
// Generated Apr 10, 2018 2:35:51 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name="roles"
    ,catalog="service_app"
)
public class Roles  implements java.io.Serializable {


     private String roleType;
     private Status status;
     private String description;
     private Set<MobSuggestedUser> mobSuggestedUsers = new HashSet(0);
     private Set<MobScheduledServiceRequest> mobScheduledServiceRequests = new HashSet(0);

    public Roles() {
    }

	
    public Roles(String roleType) {
        this.roleType = roleType;
    }
    public Roles(String roleType, Status status, String description, Set<MobSuggestedUser> mobSuggestedUsers,Set<MobScheduledServiceRequest> mobScheduledServiceRequests) {
       this.roleType = roleType;
       this.status = status;
       this.description = description;
       this.mobSuggestedUsers = mobSuggestedUsers;
       this.mobScheduledServiceRequests = mobScheduledServiceRequests;
    }
   
     @Id 

    
    @Column(name="role_type", unique=true, nullable=false, length=16)
    public String getRoleType() {
        return this.roleType;
    }
    
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    
    @Column(name="description", length=64)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="roles")
    public Set<MobSuggestedUser> getMobSuggestedUsers() {
        return this.mobSuggestedUsers;
    }
    
    public void setMobSuggestedUsers(Set<MobSuggestedUser> mobSuggestedUsers) {
        this.mobSuggestedUsers = mobSuggestedUsers;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="roles")
    public Set<MobScheduledServiceRequest> getMobScheduledServiceRequests() {
        return this.mobScheduledServiceRequests;
    }
    
    public void setMobScheduledServiceRequests(Set<MobScheduledServiceRequest> mobScheduledServiceRequests) {
        this.mobScheduledServiceRequests = mobScheduledServiceRequests;
    }


}


