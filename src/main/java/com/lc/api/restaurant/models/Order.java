package com.lc.api.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="order", schema="public")
public class Order implements Serializable {

  private static final long serialVersionUID = 1689614227461588965L;

  @Id
  @Column(name="order_id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long orderId;

  @NotNull
  @Column(name="order_no")
  private String orderNo;


  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
  private Customer customer;

  @Column(name="pmethod")
  private String pmethod;

  @Column(name="gtotal")
  private BigDecimal gtotal;


  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "orderId"
  )
 @JsonIgnoreProperties("orderId")
 private List<OrderItem> orderItems = new ArrayList<>();

  public Order() {
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getPmethod() {
    return pmethod;
  }

  public void setPmethod(String pmethod) {
    this.pmethod = pmethod;
  }

  public BigDecimal getGtotal() {
    return gtotal;
  }

  public void setGtotal(BigDecimal gtotal) {
    this.gtotal = gtotal;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }
}
