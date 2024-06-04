package org.achareh.base.service.impl;

import org.achareh.base.entity.BaseEntity;
import org.achareh.base.repository.BaseRepository;
import org.achareh.base.service.BaseService;
import org.achareh.exception.NotFoundExeption;
import org.achareh.utility.EntityValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BaseServiceImpl<T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {

    protected final R repository;
    protected final SessionFactory sessionFactory;
    private final Validator validator;

    public BaseServiceImpl(R repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
        this.validator = EntityValidator.validator;
    }


    @Override
    public T saveOrUpdate(T entity) {
        Transaction transaction = null;

//        if (!isValid(entity)) {
//            System.out.println("Invalid entity. Aborting saveOrUpdate operation.");
//            return null;
//        }
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            T t = repository.saveOrUpdate(entity);
            transaction.commit();
            System.out.println("Entity saved or updated successfully.");
            session.close();
            return t;
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<T> byId = repository.findById(id);
            session.getTransaction().commit();
            return byId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            repository.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<T> findAll = repository.findAll();
            session.getTransaction().commit();
            session.close();
            return findAll;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteById(ID id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<T> findEntity = repository.findById(id);
            findEntity.orElseThrow(() -> new NotFoundExeption(String.format("Entity with id : %s not found", id)));
            repository.delete(findEntity.get());
            session.getTransaction().commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean isValid(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
        return true;
    }
}