package org.example.repository.dao;

import org.example.repository.entities.CompetenciaEntity;
import org.example.configs.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompetenciaDao {


    public CompetenciaEntity findByName(String nomeCompetencia) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM CompetenciaEntity c WHERE c.nomeCompetencia = :nomeCompetencia", CompetenciaEntity.class)
                    .setParameter("nomeCompetencia", nomeCompetencia)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
