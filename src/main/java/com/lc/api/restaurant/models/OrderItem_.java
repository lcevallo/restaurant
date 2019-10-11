package com.lc.api.restaurant.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderItem.class)
public abstract class OrderItem_ {

	public static volatile SingularAttribute<OrderItem, Item> itemId;
	public static volatile SingularAttribute<OrderItem, Integer> quantity;
	public static volatile SingularAttribute<OrderItem, Order> orderId;
	public static volatile SingularAttribute<OrderItem, Long> orderItemId;

}

