package com.lc.api.restaurant.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="order_item", schema="public")
public class OrderItem implements Serializable {

  private static final long serialVersionUID = -2312504218740580026L;

  @Id
  @Column(name = "order_item_id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long orderItemId;

  @JoinColumn(name = "order_id", referencedColumnName = "order_id")
  @ManyToOne
  private Order orderId;

   @JoinColumn(name = "item_id", referencedColumnName = "item_id")
  @ManyToOne
  private Item itemId;

  @NotNull
  @Column(name = "quantity")
  private Integer quantity;

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public Order getOrderId() {
    return orderId;
  }

  public void setOrderId(Order orderId) {
    this.orderId = orderId;
  }

  public Item getItemId() {
    return itemId;
  }

  public void setItemId(Item itemId) {
    this.itemId = itemId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
