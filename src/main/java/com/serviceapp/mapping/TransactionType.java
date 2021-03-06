package com.serviceapp.mapping;
// Generated Mar 26, 2018 2:41:37 PM by Hibernate Tools 4.3.1


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
 * TransactionType generated by hbm2java
 */
@Entity
@Table(name="transaction_type"
    ,catalog="service_app"
)
public class TransactionType  implements java.io.Serializable {


     private String typecode;
     private Status status;
     private String descriptionReceiver;
     private String description;
     private Set<WebSmsTemplate> webSmsTemplates = new HashSet(0);
     private Set<WebEmailTemplate> webEmailTemplates = new HashSet(0);

    public TransactionType() {
    }

	
    public TransactionType(String typecode) {
        this.typecode = typecode;
    }
    public TransactionType(String typecode, Status status, String descriptionReceiver, String description, Set<WebSmsTemplate> webSmsTemplates, Set<WebEmailTemplate> webEmailTemplates) {
       this.typecode = typecode;
       this.status = status;
       this.descriptionReceiver = descriptionReceiver;
       this.description = description;
       this.webSmsTemplates = webSmsTemplates;
       this.webEmailTemplates = webEmailTemplates;
    }
   
     @Id 

    
    @Column(name="TYPECODE", unique=true, nullable=false, length=3)
    public String getTypecode() {
        return this.typecode;
    }
    
    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    
    @Column(name="DESCRIPTION_RECEIVER", length=64)
    public String getDescriptionReceiver() {
        return this.descriptionReceiver;
    }
    
    public void setDescriptionReceiver(String descriptionReceiver) {
        this.descriptionReceiver = descriptionReceiver;
    }

    
    @Column(name="DESCRIPTION", length=64)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="transactionType")
    public Set<WebSmsTemplate> getWebSmsTemplates() {
        return this.webSmsTemplates;
    }
    
    public void setWebSmsTemplates(Set<WebSmsTemplate> webSmsTemplates) {
        this.webSmsTemplates = webSmsTemplates;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="transactionType")
    public Set<WebEmailTemplate> getWebEmailTemplates() {
        return this.webEmailTemplates;
    }
    
    public void setWebEmailTemplates(Set<WebEmailTemplate> webEmailTemplates) {
        this.webEmailTemplates = webEmailTemplates;
    }




}


