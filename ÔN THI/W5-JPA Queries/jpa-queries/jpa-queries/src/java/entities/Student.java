/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * LỚP THỰC THỂ (ENTITY): Student
 * Đại diện cho bảng 'Student' trong DB.
 */
@Entity
@Table(name = "Student")
@XmlRootElement
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id // Khóa chính
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id")
    private Integer id;
    
    @Size(max = 50)
    @Column(name = "FirstName")
    private String firstName;
    
    @Size(max = 50)
    @Column(name = "LastName")
    private String lastName;
    
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Size(max = 200)
    @Column(name = "Address")
    private String address;
    
    @Size(max = 15)
    @Column(name = "Phone")
    private String phone;
    
    @Size(max = 100)
    @Column(name = "Email")
    private String email;

    public Student() {}

    // Các Getter và Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
