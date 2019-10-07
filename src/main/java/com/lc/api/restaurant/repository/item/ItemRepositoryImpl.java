package com.lc.api.restaurant.repository.item;

import com.lc.api.restaurant.models.Item;
import com.lc.api.restaurant.models.Item_;
import com.lc.api.restaurant.repository.filter.ItemFilter;
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

public class ItemRepositoryImpl implements ItemRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Item> filtrar(ItemFilter itemFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Item> criteria = builder.createQuery(Item.class);

    Root<Item> root = criteria.from(Item.class);

    Predicate[] predicates= crearRestricciones(itemFilter, builder, root);

    criteria.where(predicates);

    TypedQuery<Item> query = manager.createQuery(criteria);

    adicionarRestricionDePaginacion(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(itemFilter)) ;
  }

  private Predicate[] crearRestricciones(ItemFilter itemFilter, CriteriaBuilder builder, Root<Item> root) {
    List<Predicate> predicates = new ArrayList<>();

    if(!StringUtils.isEmpty(itemFilter.getName())){
      predicates.add( builder.like(
          builder.lower(root.get(Item_.name)),
          "%" + itemFilter.getName().toLowerCase()+"%"
          )
      );
    }

    if(itemFilter.getPrice()!= null){
      predicates.add( builder.equal(root.get(Item_.price),itemFilter.getPrice()));
    }

    return predicates.toArray(new Predicate[predicates.size()]);

  }




  private void adicionarRestricionDePaginacion(TypedQuery<Item> query, Pageable pageable) {
    int paginaActual = pageable.getPageNumber();
    int totalRegistroPorPagina = pageable.getPageSize();

    int primerRegistroDePagina = paginaActual* totalRegistroPorPagina;
    query.setFirstResult(primerRegistroDePagina);
    query.setMaxResults(totalRegistroPorPagina);
  }



  private Long total(ItemFilter itemFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<Item> root = criteria.from(Item.class);

    Predicate[] predicates = crearRestricciones(itemFilter, builder, root);

    criteria.where(predicates);
    criteria.select(builder.count(root));

    return manager.createQuery(criteria).getSingleResult();
  }



}
