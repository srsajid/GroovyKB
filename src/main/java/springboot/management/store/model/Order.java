package springboot.management.store.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sajedur on 11/3/2016.
 */
@Entity(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "ORDER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @Column(name = "TOTAL_PRICE", precision = 2)
    private double totPrice;

    @Column(name = "OREDER_DESC")
    private String orderDesc;

    @Column(name = "ORDER_DATE")
    private Date orderDt;

//    @OneToOne(optional=false,
//            cascade=CascadeType.ALL,
//            mappedBy="order",
//            targetEntity=Invoice.class
//    )
    @OneToOne(mappedBy="order")
    private Invoice invoice;

    @ManyToOne(optional=false)
//    @JoinColumn(name="CUST_ID")
    private Customer customer;


    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Date updatedTime;

}