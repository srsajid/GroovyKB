package springboot.management.store.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sajedur on 11/3/2016.
 */
@Entity(name = "ORDER_INVOICE")
public class Invoice {

    @Id
    @Column(name = "INVOICE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long invoiceId;

//    @Column(name = "ORDER_ID")
//    private long orderId;

    @Column(name = "AMOUNT_DUE", precision = 2)
    private double amountDue;

    @Column(name = "DATE_RAISED")
    private Date orderRaisedDt;

    @Column(name = "DATE_SETTLED")
    private Date orderSettledDt;

    @Column(name = "DATE_CANCELLED")
    private Date orderCancelledDt;

    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Date updatedTime;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}