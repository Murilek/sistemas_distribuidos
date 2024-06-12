package org.example.repository.dao;

import org.example.configs.hibernate.HibernateUtil;
import org.example.repository.entities.CandidatoEntity;
import org.example.repository.entities.CandidatoTokenEntity;
import org.example.repository.entities.EmpresaEntity;
import org.example.repository.entities.EmpresaTokenEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmpresaTokenDao {

    public void save(EmpresaTokenEntity entity) {
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

    public boolean delete(EmpresaTokenEntity entity) {
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

    public EmpresaTokenEntity findByEmpresa(EmpresaEntity empresa) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from EmpresaTokenEntity s where s.empresa = :empresa", EmpresaTokenEntity.class)
                    .setParameter("empresa", empresa)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public EmpresaTokenEntity findByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from EmpresaTokenEntity s where s.token = :token", EmpresaTokenEntity.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            EmpresaTokenEntity tokenEntity = session.createQuery("SELECT s FROM EmpresaTokenEntity s WHERE s.token = :token", EmpresaTokenEntity.class)
                    .setParameter("token", token)
                    .uniqueResult();
            return !(tokenEntity != null);
        } catch (Exception e) {
            return true;
        }
    }

}
