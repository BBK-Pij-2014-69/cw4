package cw4;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ContactManagerImplTest {
	ContactManager contactManager;
	Set<Contact> contacts;
	

	@Before
	public void setUp() {
		contacts = new LinkedHashSet<Contact>(); // Used LinkedHashSet as order is maintained and so easier to test.
		contacts.add(new ContactImpl(1, "Clark Kent"));
		contactManager = new ContactmangerImpl();
	}

	@After
	public void tearDown() {
		
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Ignore @Test
	public void testAddFutureMeeting() {
		assertEquals(1,contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00)));
	}
	
	@Test
	public void throwsExceptionWhenAContactIsNonExistent() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("One or more contacts do not exist");
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00));
	}
	
	@Test
	public void throwsExceptionWhenInvalidDateIsUsed() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid time/date");
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00));
	}
	

	@Ignore @Test
	public void testGetPastMeeting() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetFutureMeeting() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetMeeting() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetFutureMeetingListContact() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetFutureMeetingListCalendar() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetPastMeetingList() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testAddNewPastMeeting() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testAddMeetingNotes() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testAddNewContact() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetContactsIntArray() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetContactsString() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testFlush() {
		fail("Not yet implemented");
	}

}
