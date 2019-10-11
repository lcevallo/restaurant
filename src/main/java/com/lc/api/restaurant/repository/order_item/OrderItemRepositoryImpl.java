package com.lc.api.restaurant.repository.order_item;

import com.lc.api.restaurant.models.OrderItem;
import com.lc.api.restaurant.repository.filter.OrderItemFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class OrderItemRepositoryImpl implements OrderItemRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<OrderItem> filtrar(OrderItemFilter orderItemFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<OrderItem> criteria = builder.createQuery(OrderItem.class);

    Root<OrderItem> root = criteria.from(OrderItem.class);

    Predicate[] predicates= crearRestricciones(orderItemFilter, builder, root);

    criteria.where(predicates);

    TypedQuery<OrderItem> query = manager.createQuery(criteria);

    adicionarRestricionDePaginacion(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(orderItemFilter)) ;

  }

  private Predicate[] crearRestricciones(OrderItemFilter orderItemFilter, CriteriaBuilder builder, Root<OrderItem> root) {

    List<Predicate> predicates = new ArrayList<>();

    //Compara con un string el nombre
    if(!StringUtils.isEmpty(orderItemFilter.getOrderNo())){
      predicates.add( builder.like(
          builder.lower(root.get("orderId").get("orderNo")),
          "%" + orderItemFilter.getOrderNo().toLowerCase()+"%"
          )
      );
    }
    //Compara con un string el nombre
    if(!StringUtils.isEmpty(orderItemFilter.getNameCustomer())){
      predicates.add( builder.like(
          builder.lower(root.get("orderId").get("customer").get("name")),
          "%" + orderItemFilter.getNameCustomer().toLowerCase()+"%"
          )
      );
    }

    if(!StringUtils.isEmpty(orderItemFilter.getNameItem())){
      predicates.add( builder.like(
          builder.lower(root.get("itemId").get("name")),
          "%" + orderItemFilter.getNameItem().toLowerCase()+"%"
          )
      );
    }

    if(orderItemFilter.getPriceItem()!=null){
      predicates.add( builder.equal(root.get("itemId").get("price"),orderItemFilter.getPriceItem()));
    }

    if(orderItemFilter.getQuantity()!=null){
      predicates.add( builder.equal(root.get("quantity"),orderItemFilter.getQuantity()));
    }

    if(orderItemFilter.getOrderId()!=null){
      predicates.add( builder.equal(root.get("orderId").get("orderId"),orderItemFilter.getOrderId()));
    }

    if(orderItemFilter.getItemId()!=null){ //Aqui modifique el if
      predicates.add( builder.equal(root.get("itemId").get("itemId"),orderItemFilter.getItemId()));
    }

    return predicates.toArray(new Predicate[predicates.size()]);

  }

  private Long total(OrderItemFilter orderItemFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<OrderItem> root = criteria.from(OrderItem.class);

    Predicate[] predicates = crearRestricciones(orderItemFilter, builder, root);

    criteria.where(predicates);
    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();

  }

  private void adicionarRestricionDePaginacion(TypedQuery<OrderItem> query, Pageable pageable) {
    int paginaActual = pageable.getPageNumber();
    int totalRegistroPorPagina = pageable.getPageSize();

    int primerRegistroDePagina = paginaActual* totalRegistroPorPagina;
    query.setFirstResult(primerRegistroDePagina);
    query.setMaxResults(totalRegistroPorPagina);

  }
}
