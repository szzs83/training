package org.hibernate.tutorial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
import org.hibernate.tutorial.util.HibernateUtil;

public class EventManager {

	public EventManager() throws ParseException {
		Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2013/01/23");

		this.createAndStoreEvent("szulinap", date);
		HashSet<String> emailAddresses = new HashSet<String>();
		emailAddresses.add("szzs83@gmail.com");
		emailAddresses.add("szzs83@vipmail.hu");
		this.createAndStorePerson("Szilagyi", "Zsolt", 30, emailAddresses);

		// Person zsolti = getPersonById(2L);
		// System.out.println("~~~~~~~~~~~~~~~~~~~~");
		// System.out.println("Zsolti kora: " + zsolti.getAge());
		// System.out.println("~~~~~~~~~~~~~~~~~~~~");
	}

	public static void main(String[] args) throws ParseException {

		new EventManager();

		// HibernateUtil.getSessionFactory().close();

	}

	public static void regi_main(String[] args) throws ParseException {
		EventManager mgr = new EventManager();

		if (args[0].equals("store")) {
			mgr.createAndStoreEvent("My Event", new Date());

		} else if (args[0].equals("list")) {
			if (args[1].equals("events")) {
				mgr.listEvents();
			} else if (args.length > 1 && args[1].equals("persons")) {
				mgr.listPersons();
			}
		} else if (args[0].equals("addpersontoevent")) {
			Long eventId = mgr.createAndStoreEvent("My Event", new Date());

			HashSet<String> emails = new HashSet<String>();
			emails.add("valami@asdf.hu");
			emails.add("hihi@vipmail.hu");
			Long personId = mgr.createAndStorePerson("Lakatos", "Romeo", 0, emails);
			mgr.addPersonToEvent(personId, eventId);
			System.out.println("Added person " + personId + " to event " + eventId);
		}

		HibernateUtil.getSessionFactory().close();
	}

	private Long createAndStorePerson(String firstname, String lastname, int age, Set<String> emailAddresses) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Person person = new Person();
		person.setAge(age);
		person.setFirstname(firstname);
		person.setLastname(lastname);
		person.setEmailAddresses(emailAddresses);

		session.save(person);

		// System.out.println("***********");
		// System.out.println(person.getEmailAddresses().size());
		// System.out.println("***********");

		session.getTransaction().commit();
		return person.getId();
	}

	private Long createAndStoreEvent(String title, Date theDate) {
		System.out.println("11111111111111");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("222222222222222222");
		session.beginTransaction();
		System.out.println("33333333333333333333");
		Event theEvent = new Event();
		System.out.println("44444444444444444444");
		theEvent.setTitle(title);
		System.out.println("55555555555555555555555");
		theEvent.setDate(theDate);
		System.out.println("666666666666");

		session.save(theEvent);

		// if we call this getId() before tha save(), it will be null
		System.out.println("*** theEvent.getId() ***");
		System.out.println(theEvent.getId());
		System.out.println("*** theEvent.getId() ***");
		System.out.println("777777777777777");

		session.getTransaction().commit();
		return theEvent.getId();
	}

	private void listPersons() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List persons = session.createQuery("from Person").list();

		System.out.println("-------------------------------------------------");
		System.out.println("LISTING PERSONS");
		for (int i = 0; i < persons.size(); i++) {
			Person person = (Person) persons.get(i);

			Set emailsSet = person.getEmailAddresses();
			System.out.println(emailsSet.size());
			// String[] emails = (String[]) emailsSet.toArray();
			// System.out.println("firstname: " + person.getFirstname() +
			// " lastname: " + person.getLastname() +
			// " age: "
			// + person.getAge() + " " + person.getEvents().size() +
			// "       emails: " + emails[0] + ", "
			// + emails[1]);
			// System.out.println("firstname: " + person.getFirstname() +
			// " lastname: " + person.getLastname());
		}
		session.getTransaction().commit();

	}

	// private Person getPersonById(String userId) {
	private Person getPersonById(Long userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Person person = (Person) session.get(Person.class, userId);
		return person;

	}

	// private Person getPersonByLastname(String firstname) {
	// //Nem mukodik
	//
	// Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	// session.beginTransaction();
	// session.createQuery(arg0)
	//
	// // Person person = (Person) session.get(Person.class, firstname);
	// return person;
	//
	// }

	private void listEvents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List events = session.createQuery("from Event").list();
		session.getTransaction().commit();

		System.out.println("-------------------------------------------------");
		System.out.println("LISTING EVENTS");
		for (int i = 0; i < events.size(); i++) {
			Event theEvent = (Event) events.get(i);
			System.out.println("Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate());
		}
	}

	private void addPersonToEvent(Long personId, Long eventId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person aPerson = (Person) session.load(Person.class, personId);
		Event anEvent = (Event) session.load(Event.class, eventId);
		aPerson.getEvents().add(anEvent);

		session.getTransaction().commit();
	}

	private void addPersonToEvent2(Long personId, Long eventId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person aPerson = (Person) session
				.createQuery("select p from Person p left join fetch p.events where p.id = :pid")
				.setParameter("pid", personId).uniqueResult(); // Eager fetch
																// the
																// collection so
																// we can use it
																// detached
		Event anEvent = (Event) session.load(Event.class, eventId);

		session.getTransaction().commit();

		// End of first unit of work

		aPerson.getEvents().add(anEvent); // aPerson (and its collection) is
											// detached

		// Begin second unit of work

		Session session2 = HibernateUtil.getSessionFactory().getCurrentSession();
		session2.beginTransaction();
		session2.update(aPerson); // Reattachment of aPerson

		session2.getTransaction().commit();
	}

	private void addEmailToPerson(Long personId, String emailAddress) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person aPerson = (Person) session.load(Person.class, personId);
		// adding to the emailAddress collection might trigger a lazy load of
		// the collection
		aPerson.getEmailAddresses().add(emailAddress);

		session.getTransaction().commit();
	}

}
