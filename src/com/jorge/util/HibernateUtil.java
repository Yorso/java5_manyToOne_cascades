package com.jorge.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory(){
		try{
			// Creating the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
			return configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
			
		}
		catch(Throwable ex){
			System.err.println("Initial Sessionfactory creation failed: " + ex.getMessage().toString());
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
