package com.salo.sistemacreche.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class GenericDAO<T> {
    private final Class<T> clazz;
    private EntityManager em;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
        this.em = DBConnection.getEntityManager();
    }

    public void salvar(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar: " + e.getMessage(), e);
        }
    }

    public void atualizar(T entity) {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        try {
            em.getTransaction().begin();
            T entity = em.find(clazz, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }

    public Optional<T> buscarPorId(Long id) {
        T entity = em.find(clazz, id);
        return Optional.ofNullable(entity);
    }

    public List<T> buscarTodos() {
        String jpql = "FROM " + clazz.getSimpleName();
        return em.createQuery(jpql, clazz).getResultList();
    }
}

