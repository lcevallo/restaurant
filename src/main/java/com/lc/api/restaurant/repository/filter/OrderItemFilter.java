package com.lc.api.restaurant.repository.filter;

import java.math.BigDecimal;

public class OrderItemFilter {


  private String orderNo;
  private String nameCustomer;
  private String nameItem;
  private BigDecimal priceItem;
  private Integer quantity;
  private Long orderId;
  private Long itemId;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getNameCustomer() {
    return nameCustomer;
  }

  public void setNameCustomer(String nameCustomer) {
    this.nameCustomer = nameCustomer;
  }

  public String getNameItem() {
    return nameItem;
  }

  public void setNameItem(String nameItem) {
    this.nameItem = nameItem;
  }

  public BigDecimal getPriceItem() {
    return priceItem;
  }

  public void setPriceItem(BigDecimal priceItem) {
    this.priceItem = priceItem;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
}
