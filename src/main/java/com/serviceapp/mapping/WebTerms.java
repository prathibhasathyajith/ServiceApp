package com.serviceapp.mapping;
// Generated Mar 27, 2018 3:32:05 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * WebTerms generated by hbm2java
 */
@Entity
@Table(name="web_terms"
    ,catalog="service_app"
)
public class WebTerms  implements java.io.Serializable {


     private String versionNo;
     private Status status;
     private String terms;

    public WebTerms() {
    }

	
    public WebTerms(String versionNo) {
        this.versionNo = versionNo;
    }
    public WebTerms(String versionNo, Status status, String terms) {
       this.versionNo = versionNo;
       this.status = status;
       this.terms = terms;
    }
   
     @Id 

    
    @Column(name="VERSION_NO", unique=true, nullable=false, length=40)
    public String getVersionNo() {
        return this.versionNo;
    }
    
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    
    @Column(name="TERMS")
    public String getTerms() {
        return this.terms;
    }
    
    public void setTerms(String terms) {
        this.terms = terms;
    }




}


