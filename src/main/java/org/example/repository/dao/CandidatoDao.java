package org.example.repository.dao;

import java.util.List;

import org.example.domain.dtos.CandidatoResponseDto;
import org.example.repository.entities.CandidatoEntity;
import org.example.configs.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class CandidatoDao {

    public void save(CandidatoEntity entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }



    public CandidatoEntity findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from CandidatoEntity s where s.email = :email and s.senha = :password", CandidatoEntity.class)
                    .setParameter("email",email)
                    .setParameter("password",password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public CandidatoEntity findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from CandidatoEntity s where s.email = :email", CandidatoEntity.class)
                    .setParameter("email",email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isEmailAlreadyExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CandidatoEntity candidato = session.createQuery("SELECT s from CandidatoEntity s where s.email = :email", CandidatoEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();

            // Se o candidato não for nulo, significa que o e-mail já existe no banco de dados
             if(candidato != null)
                 return true;
             else
                 return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(CandidatoEntity entity) {
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
}