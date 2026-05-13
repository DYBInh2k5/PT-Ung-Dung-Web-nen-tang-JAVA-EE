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
/**
 * LỚP THỰC THỂ (ENTITY): Toy
 * Đại diện cho bảng 'Toy' trong cơ sở dữ liệu.
 */
@Entity
@Table(name = "Toy")
@XmlRootElement
public class Toy implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id // Đánh dấu đây là khóa chính
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
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Price")
    private BigDecimal price; // Dùng BigDecimal cho tiền tệ để chính xác
    
    @Column(name = "ExpDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDate;
    
    /**
     * MỐI QUAN HỆ: Many-to-One
     * Nhiều đồ chơi thuộc về một thương hiệu (Brand).
     * @JoinColumn: Chỉ định cột khóa ngoại 'Brand' trong bảng Toy kết nối với cột 'Id' của bảng Brand.
     */
    @JoinColumn(name = "Brand", referencedColumnName = "Id")
    @ManyToOne
    private Brand brand;

    public Toy() {}

    // Các Getter và Setter giúp Controller và JSP truy xuất dữ liệu đối tượng
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Date getExpDate() { return expDate; }
    public void setExpDate(Date expDate) { this.expDate = expDate; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }
}
