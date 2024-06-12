package org.example.repository.dao;

import org.example.configs.hibernate.HibernateUtil;
import org.example.repository.entities.CandidatoEntity;
import org.example.repository.entities.CandidatoTokenEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CandidatoTokenDao {

    public void save(CandidatoTokenEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean delete(CandidatoTokenEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return false;
    }

    public CandidatoTokenEntity findByCandidato(CandidatoEntity candidato) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from CandidatoTokenEntity s where s.candidato = :candidato", CandidatoTokenEntity.class)
                    .setParameter("candidato", candidato)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public CandidatoTokenEntity findByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from CandidatoTokenEntity s where s.token = :token", CandidatoTokenEntity.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CandidatoTokenEntity tokenEntity = session.createQuery("SELECT s FROM CandidatoTokenEntity s WHERE s.token = :token", CandidatoTokenEntity.class)
                    .setParameter("token", token)
                    .uniqueResult();
            return !(tokenEntity != null);
        } catch (Exception e) {
            return true;
        }
    }
}