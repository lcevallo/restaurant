package com.lc.api.restaurant.repository.customer;

import com.lc.api.restaurant.models.Customer;
import com.lc.api.restaurant.models.Customer_;
import com.lc.api.restaurant.repository.filter.CustomerFilter;
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

public class CustomerRepositoryImpl implements CustomerRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Customer> filtrar(CustomerFilter customerFilter, Pageable pageable) {
    //Aqui usaremos el api Criteria
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);

    Root<Customer> root = criteria.from(Customer.class);

    Predicate[] predicates= crearRestricciones(customerFilter, builder, root);


    criteria.where(predicates);

    TypedQuery<Customer> query = manager.createQuery(criteria);

    adicionarRestricionDePaginacion(query, pageable);


    return new PageImpl<>(query.getResultList(), pageable, total(customerFilter)) ;

  }




  private Predicate[] crearRestricciones(CustomerFilter customerFilter, CriteriaBuilder builder, Root<Customer> root) {
    List<Predicate> predicates = new ArrayList<>();

    if(!StringUtils.isEmpty(customerFilter.getName())){

      predicates.add( builder.like(
                                builder.lower(root.get(Customer_.name)),
                                "%" + customerFilter.getName().toLowerCase()+"%"
                                )
      );
    }


    return predicates.toArray(new Predicate[predicates.size()]);
  }


  private void adicionarRestricionDePaginacion(TypedQuery<Customer> query, Pageable pageable) {
    int paginaActual = pageable.getPageNumber();
    int totalRegistroPorPagina = pageable.getPageSize();

    int primerRegistroDePagina = paginaActual* totalRegistroPorPagina;
    query.setFirstResult(primerRegistroDePagina);
    query.setMaxResults(totalRegistroPorPagina);
  }


  private Long total(CustomerFilter customerFilter) {

    CriteriaBuilder builder = manager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<Customer> root = criteria.from(Customer.class);
    Predicate[] predicates = crearRestricciones(customerFilter, builder, root);

    criteria.where(predicates);
    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();

  }
}
