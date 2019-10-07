package com.lc.api.restaurant.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="item", schema="public")
public class Item  implements Serializable {

  private static final long serialVersionUID = 89898749960771694L;

  @Id
  @Column(name = "item_id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long itemId;

  @NotNull
  @Size(min = 3, max = 50)
  @Column(name="name")
  private String name;

  @NotNull
  @Column(name="price")
  private BigDecimal price;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
