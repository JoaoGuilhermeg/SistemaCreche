package com.salo.sistemacreche.dao;

import com.salo.sistemacreche.entidades.Matricula;
import jakarta.persistence.EntityManager;
import java.util.List;

public class MatriculaDAO extends GenericDAO<Matricula> {

    public MatriculaDAO() {
        super(Matricula.class);
    }

    public List<Matricula> buscarMatriculasAtivas() {
        EntityManager em = DBConnection.getEntityManager();
        return em.createQuery(
                "SELECT m FROM Matricula m WHERE m.situacaoMatricula = 'ATIVA'",
                Matricula.class
        ).getResultList();
    }

    public List<Matricula> buscarPorAnoLetivo(Integer ano) {
        EntityManager em = DBConnection.getEntityManager();
        return em.createQuery(
                "SELECT m FROM Matricula m WHERE m.anoLetivo = :ano",
                Matricula.class
        ).setParameter("ano", ano).getResultList();
    }
}