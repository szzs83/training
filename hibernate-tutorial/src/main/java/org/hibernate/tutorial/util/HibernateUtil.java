package org.hibernate.tutorial.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			// Configuration cfg = new Configuration();
			// cfg.addResource(resourceName); stb.
			// így is lehetne meghatározni a configuration-t...
			// vagy egyből class-t hozzáadni:
			// Configuration cfg = new Configuration()
			// .addClass(org.hibernate.auction.Item.class)
			// .addClass(org.hibernate.auction.Bid.class);

			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}