package cw4;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContactManagerImplTest {
	ContactManager contactManager;
	Set<Contact> contacts;
	
	

	@Before
	public void setUp() throws Exception {
		contacts = new LinkedHashSet<Contact>();
		contacts.add(new ContactImpl(1, "Clark Kent"));
		contactManager = new ContactmangerImpl();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testAddFutureMeeting() {
		assertEquals(1,contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00)));
		assertEquals("Invalid time/date",contactManager.addFutureMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00)));
		assertEquals("One or more contacts do not exist",contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00)));
	}

	@Test
	public void testGetPastMeeting() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFutureMeeting() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMeeting() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFutureMeetingListContact() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFutureMeetingListCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPastMeetingList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewPastMeeting() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMeetingNotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewContact() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContactsIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContactsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFlush() {
		fail("Not yet implemented");
	}

}
