package org.example.repository.dao;


import java.util.List;

import org.example.domain.dtos.CandidatoResponseDto;
import org.example.repository.entities.CandidatoEntity;
import org.example.repository.entities.EmpresaEntity;
import org.example.configs.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class EmpresaDao {

    public void save(EmpresaEntity entity) {
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

    public EmpresaEntity findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from EmpresaEntity s where s.email = :email and s.senha = :password", EmpresaEntity.class)
                    .setParameter("email",email)
                    .setParameter("password",password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public EmpresaEntity findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from EmpresaEntity s where s.email = :email", EmpresaEntity.class)
                    .setParameter("email",email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isEmailAlreadyExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            EmpresaEntity empresa = session.createQuery("SELECT s from EmpresaEntity s where s.email = :email", EmpresaEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();

            // Se o Empresa não for nulo, significa que o e-mail já existe no banco de dados
            if(empresa != null)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(EmpresaEntity entity) {
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
