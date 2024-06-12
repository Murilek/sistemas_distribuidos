package org.example.configs.hibernate;

import java.util.Properties;

import org.example.repository.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "org.mariadb.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mariadb://localhost:3306/sistema?useSSL=false&allowPublicKeyRetrieval=true");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "");
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.LOG_JDBC_WARNINGS, "false");
                settings.put(Environment.FORMAT_SQL, "false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.USE_SQL_COMMENTS, "thread");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MariaDBDialect");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(CandidatoEntity.class);
                configuration.addAnnotatedClass(CandidatoTokenEntity.class);
                configuration.addAnnotatedClass(EmpresaEntity.class);
                configuration.addAnnotatedClass(EmpresaTokenEntity.class);
                configuration.addAnnotatedClass(VagaEntity.class);
                configuration.addAnnotatedClass(VagaCompetenciaEntity.class);
                configuration.addAnnotatedClass(CompetenciaEntity.class);
                configuration.addAnnotatedClass(CandidatoCompetenciaEntity.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}