package org.achareh.base.service;

import org.achareh.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    T saveOrUpdate(T entity);

    Optional<T> findById(ID id);

    void delete(T entity);

    List<T> findAll();

    void deleteById(ID id);

    boolean isValid(T t);

}
