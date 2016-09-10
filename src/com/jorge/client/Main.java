package com.jorge.client;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jorge.entity.Guide;
import com.jorge.entity.Student;
import com.jorge.util.HibernateUtil;

/**
 * Association mappings => MANY TO ONE
 * Student class and Guide class are both entities
 *
 */
public class Main {

	public static void main(String[] args) {
		BasicConfigurator.configure(); // Necessary for configure log4j. It must be the first line in main method
								       // log4j.properties must be in /src directory
		
		Logger  logger = Logger.getLogger(Main.class.getName());
		logger.debug("log4j configured correctly and logger set");

		logger.debug("getting session factory from HibernateUtil.java");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = session.getTransaction();
		
		try {
			
			logger.debug("beginning cascading persist transaction");
			txn.begin(); // Beginning transaction

			logger.debug("setting data in Person object");
			Guide guide = new Guide("GD200331", "Homer Simpson", 1200);
			Student student = new Student("ST109883", "Moe Szyslak", guide);
			
			// It's not necessary, we can use persist() method to save data the both student and guide tables
			// In Student.java entity we have this annotation: @ManyToOne(cascade={CascadeType.PERSIST}) to do that
			/*
			logger.debug("first, saving guide data in DB");
			session.save(guide);
			
			logger.debug("then, saving student data in DB");
			session.save(student);
			*/

			logger.debug("now, we can save data in DB using cascade");
			session.persist(student);
			
			guide = new Guide("GD200332", "Bart Simpson", 1500);
			student = new Student("ST109884", "Lisa Simpson", guide);
			
			logger.debug("now, we can save data in DB using cascade");
			session.persist(student);

			logger.debug("making commit of cascading persist transactions");
			txn.commit(); // Making commit
			
			
			
			// Same for delete:  @ManyToOne(cascade={CascadeType.REMOVE}). You delete a student row, it deletes its linked guide row
			logger.debug("beginning cascading remove transaction");
			txn = session.getTransaction();
			txn.begin();
			
			logger.debug("getting student info (row)");
			student = (Student) session.get(Student.class, 1L); // id 1 in DB
			
			logger.debug("deleting student (and its linked guide row automatically because of CascadeType.REMOVE");
			session.delete(student);;
			
			logger.debug("making commit of cascading remove transactions");
			txn.commit(); // Making commit
		
		} catch (Exception e) {
			if (txn != null) {
				logger.error("something was wrong, making rollback of transactions");
				txn.rollback(); // If something was wrong, we make rollback
			}
			logger.error("Exception: " + e.getMessage().toString());
		} finally {
			if (session != null) {
				logger.debug("close session");
				session.close();
			}
		}
	}

}
