package com.lc.api.restaurant.models;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, String> orderNo;
	public static volatile SingularAttribute<Order, Long> orderId;
	public static volatile SingularAttribute<Order, BigDecimal> gtotal;
	public static volatile SingularAttribute<Order, String> pmethod;
	public static volatile SingularAttribute<Order, Customer> customer;

}

