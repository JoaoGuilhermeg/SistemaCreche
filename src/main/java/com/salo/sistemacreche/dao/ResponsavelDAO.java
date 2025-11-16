package com.salo.sistemacreche.dao;

import com.salo.sistemacreche.entidades.Pessoa;
import com.salo.sistemacreche.entidades.Responsavel;
import com.salo.sistemacreche.entidades.TipoResponsavel;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ResponsavelDAO {

    public void salvar(Pessoa pessoa, Responsavel responsavel) {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();
            DBConnection.beginTransaction();

            // Primeiro salva a Pessoa
            em.persist(pessoa);

            // Associa a Pessoa ao Responsável e salva
            responsavel.setPessoa(pessoa);
            em.persist(responsavel);

            DBConnection.commitTransaction();
            System.out.println("✅ Responsável salvo com ID: " + responsavel.getId());

        } catch (Exception e) {
            DBConnection.rollbackTransaction();
            throw new RuntimeException("Erro ao salvar responsável: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                DBConnection.closeEntityManager();
            }
        }
    }

    public List<Responsavel> listarTodos() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            return em.createQuery(
                    "SELECT r FROM Responsavel r JOIN FETCH r.pessoa p ORDER BY p.nome",
                    Responsavel.class
            ).getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar responsáveis: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                DBConnection.closeEntityManager();
            }
        }
    }

    public void excluir(Responsavel responsavel) {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();
            DBConnection.beginTransaction();

            Responsavel responsavelGerenciado = em.find(Responsavel.class, responsavel.getId());
            if (responsavelGerenciado != null) {
                // Remove o responsável
                em.remove(responsavelGerenciado);

                // Opcional: Remove a pessoa também se não for usada em outros lugares
                // Pessoa pessoa = responsavelGerenciado.getPessoa();
                // em.remove(pessoa);

                System.out.println("✅ Responsável excluído: " + responsavelGerenciado.getPessoa().getNome());
            }

            DBConnection.commitTransaction();

        } catch (Exception e) {
            DBConnection.rollbackTransaction();
            throw new RuntimeException("Erro ao excluir responsável: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                DBConnection.closeEntityManager();
            }
        }
    }

    public Pessoa buscarPessoaPorCpf(String cpf) {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            List<Pessoa> resultado = em.createQuery(
                            "SELECT p FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class)
                    .setParameter("cpf", cpf)
                    .getResultList();

            return resultado.isEmpty() ? null : resultado.get(0);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pessoa por CPF: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                DBConnection.closeEntityManager();
            }
        }
    }

    // Método para buscar TipoResponsavel (você precisará criar essa entidade também)
    public TipoResponsavel buscarTipoResponsavelPadrao() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            // Assumindo que você tem um tipo padrão, por exemplo ID = 1
            return em.find(TipoResponsavel.class, 1L);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar tipo responsável: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                DBConnection.closeEntityManager();
            }
        }
    }
}