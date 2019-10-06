package com.lc.api.restaurant.repository.Order;


import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.models.Order_;
import com.lc.api.restaurant.repository.filter.OrderFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class OrderRepositoryImpl implements OrderRepositoryQuery{

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Order> filtrar(OrderFilter orderFilter, Pageable pageable) {


    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Order> criteria = builder.createQuery(Order.class);

    Root<Order> root = criteria.from(Order.class);

    Predicate[] predicates= crearRestricciones(orderFilter, builder, root);

    criteria.where(predicates);

    TypedQuery<Order> query = manager.createQuery(criteria);

    adicionarRestricionDePaginacion(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(orderFilter)) ;

  }

  public List<Order> findOrders(OrderFilter orderFilter){

    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<Order> c = cb.createQuery(Order.class);
    Root<Order> order = c.from(Order.class);
    c.select(order);
    List<Predicate> criteria = new ArrayList<>();

    if(!StringUtils.isEmpty(orderFilter.getPmethod())){
      ParameterExpression<String> p = cb.parameter(String.class,"pmethod");
      criteria.add(cb.like(
                            cb.lower(order.get("pmethod"))
                                ,p
                          )
                  );
    }

    if(!StringUtils.isEmpty(orderFilter.getOrderNo())){
      ParameterExpression<String> p = cb.parameter(String.class,"orderNo");
      criteria.add(cb.like(
          cb.lower(order.get("orderNo"))
          ,p
          )
      );
    }


    if(!StringUtils.isEmpty(orderFilter.getNombre())){
      ParameterExpression<String> p = cb.parameter(String.class,"name");
      criteria.add(cb.like(
          cb.lower(order.get("customer").get("name"))
          ,p
          )
      );
    }

    TypedQuery<Order> q = manager.createQuery(c);
    if(!StringUtils.isEmpty(orderFilter.getPmethod())) {q.setParameter("pmethod", "%" + orderFilter.getPmethod().toLowerCase()+"%" );}
    if(!StringUtils.isEmpty(orderFilter.getOrderNo())) {q.setParameter("orderNo","%" + orderFilter.getOrderNo().toLowerCase()+"%");}
    if(!StringUtils.isEmpty(orderFilter.getNombre())) {q.setParameter("name","%" + orderFilter.getNombre().toLowerCase()+"%");}

    return q.getResultList();

  }



   private Predicate[] crearRestricciones(OrderFilter orderFilter, CriteriaBuilder builder, Root<Order> root) {
    List<Predicate> predicates = new ArrayList<>();

    if(!StringUtils.isEmpty(orderFilter.getPmethod())){
      predicates.add( builder.like(
                                    builder.lower(root.get(Order_.pmethod)),
                                    "%" + orderFilter.getPmethod().toLowerCase()+"%"
                              )
                     );
    }

    if(!StringUtils.isEmpty(orderFilter.getOrderNo())){
      predicates.add( builder.like(
          builder.lower(root.get(Order_.orderNo)),
          "%" + orderFilter.getOrderNo().toLowerCase()+"%"
          )
      );
    }

//TODO: no me funciona aun pero ya lo arreglare
    if(!StringUtils.isEmpty(orderFilter.getNombre())){

      predicates.add( builder.like(
          builder.lower(root.get("customer").get("name")),
          "%" + orderFilter.getNombre().toLowerCase()+"%"
          )
      );

    }

    return predicates.toArray(new Predicate[predicates.size()]);

  }




  private void adicionarRestricionDePaginacion(TypedQuery<Order> query, Pageable pageable) {
    int paginaActual = pageable.getPageNumber();
    int totalRegistroPorPagina = pageable.getPageSize();

    int primerRegistroDePagina = paginaActual* totalRegistroPorPagina;
    query.setFirstResult(primerRegistroDePagina);
    query.setMaxResults(totalRegistroPorPagina);
  }



  private Long total(OrderFilter orderFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<Order> root = criteria.from(Order.class);

    Predicate[] predicates = crearRestricciones(orderFilter, builder, root);

    criteria.where(predicates);
    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();
  }
}
