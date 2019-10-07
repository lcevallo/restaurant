package com.lc.api.restaurant.repository.filter;

import java.math.BigDecimal;

public class ItemFilter {

  private String name;
  private BigDecimal price;


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
