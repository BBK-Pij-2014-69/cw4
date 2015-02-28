package cw4;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeetingImplTest {
	Meeting meeting;
	Set<Contact> contacts = new HashSet<Contact>();
	Calendar date;
 
	@Before
	public void setUp() {
		Contact contact0 = new ContactImpl(1234, "John Jones");
		Contact contact1 = new ContactImpl(1234, "John Jones");
		contacts.add(contact0);
		contacts.add(contact1);
		date = new GregorianCalendar(2016, 01, 23, 11, 00);
		meeting = new MeetingImpl(1234, date, contacts);
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
		Set<Contact> expectedContacts = new HashSet<Contact>();
		Contact contact0 = new ContactImpl(1234, "John Jones");
		Contact contact1 = new ContactImpl(1234, "John Jones");
		expectedContacts.add(contact0);
		expectedContacts.add(contact1);
		assertEquals(expectedContacts,meeting.getContacts());
	}

}
