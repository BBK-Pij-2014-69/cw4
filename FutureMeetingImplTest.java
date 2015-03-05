package cw4;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FutureMeetingImplTest {
	Meeting meeting;
	Set<Contact> contacts = new LinkedHashSet<Contact>();
	Calendar date;
 
	@Before
	public void setUp() {
		Contact contact0 = new ContactImpl(1234, "John Jones");
		Contact contact1 = new ContactImpl(3456, "Clark Kent");
		contacts.add(contact0);
		contacts.add(contact1);
		date = new GregorianCalendar(2016, 01, 23, 11, 00);
		meeting = new FutureMeetingImpl(1234, date, contacts);
	}

	@After
	public void tearDown() {
		meeting = null;
	}

	@Test
	public void testGetId() {
		assertEquals(1234, meeting.getId());
	}

	@Test
	public void testGetDate() {
		assertEquals(new GregorianCalendar(2016, 01, 23, 11, 00), meeting.getDate());
	}

	@Test
	public void testGetContacts() {
		Contact[] actualContacts = meeting.getContacts().toArray(new Contact[meeting.getContacts().size()]);
		assertEquals("John Jones",actualContacts[0].getName());
		assertEquals("Clark Kent",actualContacts[1].getName());
	}

}
