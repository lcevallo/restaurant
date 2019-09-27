package com.lc.api.restaurant.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="customer", schema="public")
public class Customer implements Serializable {

  private static final long serialVersionUID = 8160084695039433449L;

  @Id
  @Column(name = "customer_id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long customerId;

  @NotNull
  @Column(name="name")
  private String name;


  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
