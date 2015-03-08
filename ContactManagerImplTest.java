package cw4;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ContactManagerImplTest {
	ContactManager contactManager;
	Set<Contact> contacts = new HashSet<Contact>();
	Set<Contact> contactsEmpty = new HashSet<Contact>();
	Set<Contact> contactsNonExisting = new HashSet<Contact>();

	@Before
	public void setUp() {
		contacts.add(new ContactImpl(1,"Diana Prince"));
		contactsNonExisting.add(new ContactImpl(2, "Arthur Curry"));
		contactManager = new ContactmangerImpl();
		contactManager.addNewContact("Diana Prince", "I AM WONDER WOMAN");
		contactManager.addNewPastMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00), "past meeting notes");
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00));
	}
	
	@After
	public void tearDown() {
		contactManager = null;
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testAddFutureMeeting() {
		assertEquals(3,contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00)));
	}
	
	//addFutureMeeting()
	@Test
	public void throwsExceptionWhenAContactIsNonExistent() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("One or more contacts do not exist");
		contactManager.addFutureMeeting(contactsNonExisting, new GregorianCalendar(2016, 00, 23, 12, 00));
	}
	
	//addFutureMeeting()
	@Test
	public void throwsExceptionWhenInvalidDateIsUsed() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid time/date");
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00));
	}
	

	@Test
	public void testGetPastMeeting() {
		assertEquals(1, contactManager.getPastMeeting(1).getId());
		assertNull(contactManager.getPastMeeting(10));
	}

	//getPastMeeting()
	@Test 
	public void throwsExceptionDateIsFuture() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("This id is for a future meeting");
		contactManager.getPastMeeting(2);
	}
	
	@Test
	public void testGetFutureMeeting() {
		contactManager.addNewContact("Diana Prince", "I AM WONDER WOMAN");
		assertEquals(2,contactManager.getFutureMeeting(2).getId());
		assertEquals(contacts, contactManager.getFutureMeeting(2).getContacts());
		assertNull(contactManager.getFutureMeeting(10));
	}
	
	//getFutureMeeting()
	@Test 
	public void throwsExceptionDateIsPast() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("This id is for a past meeting");
		contactManager.getFutureMeeting(1);
	}

	@Test
	public void testGetMeeting() {
		contactManager.addNewContact("Diana Prince", "I AM WONDER WOMAN");
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 12, 00));
		assertEquals(1,contactManager.getMeeting(1).getId());
		assertNull(contactManager.getMeeting(10));
	}
	
	@Test
	public void testGetFutureMeetingListContact() {
		contactManager.addNewContact("Arthur Curry", "I AM AQUAMAN");
		assertEquals(1, contactManager.getFutureMeetingList(new ContactImpl(1, "Diana Prince")).size());
		assertEquals(0, contactManager.getFutureMeetingList(new ContactImpl(2, "Arthur Curry")).size());
	}
	
	//getFutureMeetingList()
	@Test 
	public void throwsExceptionContactNotExists() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Contact does not exist");
		contactManager.getFutureMeetingList(new ContactImpl(2, "Arthur Curry"));
	}

	@Ignore @Test
	public void testGetFutureMeetingListCalendar() {
		fail("Not yet implemented");
	}

	@Ignore @Test
	public void testGetPastMeetingList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewPastMeeting() {
		contactManager.addNewPastMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00), "No New Business");
		assertEquals(1,contactManager.getMeeting(1).getId());
	}
	
	//addNewPastMeeting()
	@Test
	public void throwsExceptionNonExistantContact() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("One or more contacts do not exist");
		contactManager.addNewPastMeeting(contactsNonExisting, new GregorianCalendar(2014, 00, 23, 12, 00), "No New Business");
	}
	
	//addNewPastMeeting()
	@Test
	public void throwsExceptionEmptyList() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Contacts list is empty");
		contactManager.addNewPastMeeting(contactsEmpty, new GregorianCalendar(2014, 00, 23, 12, 00), "No New Business");
	}
	
	//addNewPastMeeting()
	@Test
	public void throwsExceptionNullContacts() {
		thrown.expect(NullPointerException.class);
		contactManager.addNewPastMeeting(null, new GregorianCalendar(2014, 00, 23, 12, 00), "No New Business");
	}
	
	//addNewPastMeeting()
	@Test
	public void throwsExceptionNullCalender() {
		thrown.expect(NullPointerException.class);
		contactManager.addNewPastMeeting(contacts, null, "No New Business");
	}
	
	//addNewPastMeeting()
	@Test
	public void throwsExceptionNullNotes() {
		thrown.expect(NullPointerException.class);
		contactManager.addNewPastMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00), null);
	}
	
	@Ignore @Test
	public void testAddMeetingNotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewContact() {
		contactManager.addNewContact("Bruce Wayne", "I AM BATMAN");
		Contact expected = new ContactImpl(2,"Bruce Wayne");
		assertTrue(contactManager.getContacts("Bruce Wayne").contains(expected));
	}
	
	// addNewContac()
	@Test
	public void throwsExceptionWhenNameIsNull() {
		thrown.expect(NullPointerException.class);
		contactManager.addNewContact(null, "not null");
	}
	
	// addNewContac()
	@Test
	public void throwsExceptionWhenNotesIsNull() {
		thrown.expect(NullPointerException.class);
		contactManager.addNewContact("not null", null);
	}

	@Ignore @Test
	public void testGetContactsIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContactsString() {
		contactManager.addNewContact("Clark Kent", "I AM SUPERMAN");
		Contact expected = new ContactImpl(2,"Clark Kent");
		assertTrue(contactManager.getContacts("Clark Kent").contains(expected));
	}
	
	// getContacts()
	@Test
	public void throwsExceptionWhenGetContactsParameterIsNull() {
		thrown.expect(NullPointerException.class);
		String name = null;
		contactManager.getContacts(name);
	}

	@Ignore @Test
	public void testFlush() {
		fail("Not yet implemented");
	}

}
