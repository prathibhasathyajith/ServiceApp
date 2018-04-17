package com.serviceapp.mapping;
// Generated Apr 17, 2018 10:56:46 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * WebBassQualification generated by hbm2java
 */
@Entity
@Table(name="web_bass_qualification"
    ,catalog="service_app"
)
public class WebBassQualification  implements java.io.Serializable {


     private Integer id;
     private String description;
     private Set<MobBassData> mobBassDatas = new HashSet(0);

    public WebBassQualification() {
    }

    public WebBassQualification(String description, Set<MobBassData> mobBassDatas) {
       this.description = description;
       this.mobBassDatas = mobBassDatas;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="webBassQualification")
    public Set<MobBassData> getMobBassDatas() {
        return this.mobBassDatas;
    }
    
    public void setMobBassDatas(Set<MobBassData> mobBassDatas) {
        this.mobBassDatas = mobBassDatas;
    }




}

