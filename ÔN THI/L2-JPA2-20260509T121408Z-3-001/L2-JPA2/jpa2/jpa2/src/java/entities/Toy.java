/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PHT
 */
@Entity
@Table(name = "Toy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Toy.findAll", query = "SELECT t FROM Toy t")
    , @NamedQuery(name = "Toy.findById", query = "SELECT t FROM Toy t WHERE t.id = :id")
    , @NamedQuery(name = "Toy.findByName", query = "SELECT t FROM Toy t WHERE t.name = :name")
    , @NamedQuery(name = "Toy.findByPrice", query = "SELECT t FROM Toy t WHERE t.price = :price")
    , @NamedQuery(name = "Toy.findByExpDate", query = "SELECT t FROM Toy t WHERE t.expDate = :expDate")})
public class Toy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "ExpDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDate;
    @JoinColumn(name = "Brand", referencedColumnName = "Id")
    @ManyToOne
    private Brand brand;

    public Toy() {
    }

    public Toy(String id) {
        this.id = id;
    }

    public Toy(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Toy(String id, String name, BigDecimal price, Date expDate, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expDate = expDate;
        this.brand = brand;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Toy)) {
            return false;
        }
        Toy other = (Toy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Toy[ id=" + id + " ]";
    }
    
}
