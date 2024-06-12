package org.example.repository.dao;

import org.example.repository.entities.VagaEntity;
import org.example.configs.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class VagaDao {

    public void save(VagaEntity entity) {
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

    public VagaEntity findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(VagaEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<VagaEntity> findByEmpresaId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM VagaEntity WHERE empresa.idEmpresa = :empresaId";
            return session.createQuery(hql, VagaEntity.class)
                    .setParameter("empresaId", id)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(VagaEntity entity) {
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
    }
}
