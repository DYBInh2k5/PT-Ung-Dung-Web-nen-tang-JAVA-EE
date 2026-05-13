package entities;

import entities.Brand;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-03-27T19:51:49")
@StaticMetamodel(Toy.class)
public class Toy_ { 

    public static volatile SingularAttribute<Toy, BigDecimal> price;
    public static volatile SingularAttribute<Toy, String> name;
    public static volatile SingularAttribute<Toy, String> id;
    public static volatile SingularAttribute<Toy, Brand> brand;
    public static volatile SingularAttribute<Toy, Date> expDate;

}