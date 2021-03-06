package com.serviceapp.mapping;
// Generated Mar 19, 2018 7:41:02 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Status generated by hbm2java
 */
@Entity
@Table(name="status"
    ,catalog="service_app"
)
public class Status  implements java.io.Serializable {


     private String statuscode;
     private String category;
     private String description;
     private Set<Systemuser> systemusers = new HashSet(0);
     private Set<MobFaq> mobFaqs = new HashSet(0);
     private Set<TransactionType> transactionTypes = new HashSet(0);
     private Set<WebTerms> webTermses = new HashSet(0);
     private Set<MobSuggestedUser> mobSuggestedUsers = new HashSet(0);
     private Set<MobUser> mobUsers = new HashSet(0);
     private Set<Roles> roleses = new HashSet(0);
     private Set<MobServiceRequest> mobServiceRequests = new HashSet(0);

    public Status() {
    }

	
    public Status(String statuscode) {
        this.statuscode = statuscode;
    }
    public Status(String statuscode, String category, String description, Set<Systemuser> systemusers,Set<MobFaq> mobFaqs,Set<TransactionType> transactionTypes,Set<WebTerms> webTermses,Set<MobSuggestedUser> mobSuggestedUsers,Set<MobUser> mobUsers,Set<Roles> roleses,Set<MobServiceRequest> mobServiceRequests) {
       this.statuscode = statuscode;
       this.category = category;
       this.description = description;
       this.systemusers = systemusers;
       this.mobFaqs = mobFaqs;
       this.transactionTypes = transactionTypes;
       this.webTermses = webTermses;
       this.mobSuggestedUsers = mobSuggestedUsers;
       this.mobUsers = mobUsers;
       this.roleses = roleses;
       this.mobServiceRequests = mobServiceRequests;
    }
   
     @Id 

    
    @Column(name="status_code", unique=true, nullable=false, length=10)
    public String getStatuscode() {
        return this.statuscode;
    }
    
    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    
    @Column(name="category", length=45)
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<Systemuser> getSystemusers() {
        return this.systemusers;
    }
    
    public void setSystemusers(Set<Systemuser> systemusers) {
        this.systemusers = systemusers;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<MobFaq> getMobFaqs() {
        return this.mobFaqs;
    }
    
    public void setMobFaqs(Set<MobFaq> mobFaqs) {
        this.mobFaqs = mobFaqs;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<TransactionType> getTransactionTypes() {
        return this.transactionTypes;
    }
    
    public void setTransactionTypes(Set<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }
@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<WebTerms> getWebTermses() {
        return this.webTermses;
    }
    
    public void setWebTermses(Set<WebTerms> webTermses) {
        this.webTermses = webTermses;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<MobSuggestedUser> getMobSuggestedUsers() {
        return this.mobSuggestedUsers;
    }
    
    public void setMobSuggestedUsers(Set<MobSuggestedUser> mobSuggestedUsers) {
        this.mobSuggestedUsers = mobSuggestedUsers;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<MobUser> getMobUsers() {
        return this.mobUsers;
    }
    
    public void setMobUsers(Set<MobUser> mobUsers) {
        this.mobUsers = mobUsers;
    }
    
@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<Roles> getRoleses() {
        return this.roleses;
    }
    
    public void setRoleses(Set<Roles> roleses) {
        this.roleses = roleses;
    }
@OneToMany(fetch=FetchType.LAZY, mappedBy="status")
    public Set<MobServiceRequest> getMobServiceRequests() {
        return this.mobServiceRequests;
    }
    
    public void setMobServiceRequests(Set<MobServiceRequest> mobServiceRequests) {
        this.mobServiceRequests = mobServiceRequests;
    }

}


