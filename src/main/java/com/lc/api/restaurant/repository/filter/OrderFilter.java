package com.lc.api.restaurant.repository.filter;

import com.lc.api.restaurant.models.Customer;

public class OrderFilter {

  private String pmethod;
  private String orderNo;
  private Customer customer;
  private String nombre;

  public String getPmethod() {
    return pmethod;
  }

  public void setPmethod(String pmethod) {
    this.pmethod = pmethod;
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

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
}
