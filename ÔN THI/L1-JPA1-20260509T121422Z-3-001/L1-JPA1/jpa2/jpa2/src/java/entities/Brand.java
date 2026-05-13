/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PHT
 */
/**
 * LỚP THỰC THỂ (ENTITY): Brand
 * Đại diện cho bảng 'Brand' (Thương hiệu) trong cơ sở dữ liệu.
 */
@Entity
@Table(name = "Brand")
@XmlRootElement
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id // Khóa chính của bảng Brand
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
    
    /**
     * MỐI QUAN HỆ: One-to-Many
     * Một thương hiệu (Brand) có thể có nhiều đồ chơi (Toy).
     * mappedBy = "brand": Cho biết mối quan hệ này được quản lý bởi biến 'brand' trong lớp Toy.
     */
    @OneToMany(mappedBy = "brand")
    private List<Toy> toyList;

    public Brand() {}

    // Các phương thức Getter/Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlTransient // Không bao gồm danh sách toy khi chuyển sang XML/JSON để tránh vòng lặp vô tận
    public List<Toy> getToyList() { return toyList; }
    public void setToyList(List<Toy> toyList) { this.toyList = toyList; }
}
