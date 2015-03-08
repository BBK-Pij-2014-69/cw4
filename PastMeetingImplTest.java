package cw4;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PastMeetingImplTest {
	PastMeeting pastMeeting;
	Set<Contact> contacts = new LinkedHashSet<Contact>();
	Calendar date;

	@Before
	public void setUp() {
		Contact contact0 = new ContactImpl(1234, "John Jones");
		Contact contact1 = new ContactImpl(3456, "Clark Kent");
		contacts.add(contact0);
		contacts.add(contact1);
		date = new GregorianCalendar(2016, 01, 23, 11, 00);
		pastMeeting = new PastMeetingImpl(1234, date, contacts, "Clark was picking his nose");
	}

	@After
	public void tearDown() {
		pastMeeting = null;
	}

	@Test
	public void testGetNotes() {
		assertEquals("Clark was picking his nose", pastMeeting.getNotes());
	}

	@Test
	public void testGetId() {
		assertEquals(1234, pastMeeting.getId());
	}

	@Test
	public void testGetDate() {
		assertEquals(new GregorianCalendar(2016, 01, 23, 11, 00), pastMeeting.getDate());
	}

	@Test
	public void testGetContacts() {
		Contact[] actualContacts = pastMeeting.getContacts().toArray(new Contact[pastMeeting.getContacts().size()]);
		assertEquals("John Jones",actualContacts[0].getName());
		assertEquals("Clark Kent",actualContacts[1].getName());
	}

}
