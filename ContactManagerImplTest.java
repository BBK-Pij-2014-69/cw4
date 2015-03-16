package cw4;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ContactManagerImplTest {
	ContactManager contactManager;
	Set<Contact> contacts = new HashSet<Contact>();
	Set<Contact> contactsEmpty = new HashSet<Contact>();
	Set<Contact> contactsNonExisting = new HashSet<Contact>();
	Contact DianaPrince = new ContactImpl(1,"Diana Prince");
	Contact ArthurCurry = new ContactImpl(2, "Arthur Curry");
	Contact ClarkKent = new ContactImpl(3, "Clark Kent");
	Contact BruceWayne = new ContactImpl(4, "Bruce Wayne");
	Contact BillyBatson = new ContactImpl(5, "Billy Batson");

	@Before
	public void setUp() {
		DianaPrince.addNotes("I AM WONDER WOMAN");
		ArthurCurry.addNotes("I AM AQUAMAN");
		ClarkKent.addNotes("I AM SUPERMAN");
		BruceWayne.addNotes("I AM BATMAN");
		BillyBatson.addNotes("I AM SHAZAM");
		contacts.add(DianaPrince);
		contactsNonExisting.add(ArthurCurry);
		contactManager = new ContactManagerImpl();
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
	public void testConstructor(){
		contactManager.flush();
		contactManager = new ContactManagerImpl();
		Set<Contact> myset = contactManager.getContacts(1);
		for (Contact c : myset){ assertEquals(1, c.getId());}
		assertEquals(1,contactManager.getContacts("Diana Prince").size());
		assertTrue(contactManager.getContacts(1).containsAll(contacts));
		File file = new File("contacts.txt");
		try{
			file.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
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
		assertEquals(1, contactManager.getFutureMeetingList(DianaPrince).size());
		assertTrue(contactManager.getFutureMeetingList(DianaPrince).get(0) instanceof FutureMeetingImpl);
		assertEquals(0, contactManager.getFutureMeetingList(ArthurCurry).size());
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 10, 00));
		//checks that first element time is before second element time.
		assertTrue(contactManager.getFutureMeetingList(DianaPrince).get(0).getDate().before(contactManager.getFutureMeetingList(DianaPrince).get(1).getDate()));
	}
	
	//getFutureMeetingList()
	@Test 
	public void throwsExceptionContactNotExists() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Contact does not exist");
		contactManager.getFutureMeetingList(ArthurCurry);
	}

	@Test
	public void testGetFutureMeetingListCalendar() {
		assertEquals(1, contactManager.getFutureMeetingList(new GregorianCalendar(2014, 00, 23)).size());
		assertEquals(2014, contactManager.getFutureMeetingList(new GregorianCalendar(2014, 00, 23)).get(0).getDate().get(Calendar.YEAR));
		assertEquals(1, contactManager.getFutureMeetingList(new GregorianCalendar(2016, 00, 23)).size());
		assertEquals(2016, contactManager.getFutureMeetingList(new GregorianCalendar(2016, 00, 23)).get(0).getDate().get(Calendar.YEAR));
	}

	@Test
	public void testGetPastMeetingList() {
		contactManager.addNewContact("Arthur Curry", "I AM AQUAMAN");
		assertEquals(1, contactManager.getPastMeetingList(DianaPrince).size());
		assertTrue(contactManager.getPastMeetingList(DianaPrince).get(0) instanceof PastMeetingImpl);
		assertEquals(0, contactManager.getPastMeetingList(ArthurCurry).size());
		contactManager.addNewPastMeeting(contacts, new GregorianCalendar(2014, 00, 23, 10, 00), "test");
		assertEquals(2, contactManager.getPastMeetingList(DianaPrince).size());
		assertEquals("test",contactManager.getPastMeetingList(DianaPrince).get(0).getNotes());
		//checks that first element time is before second element time.
		assertTrue(contactManager.getPastMeetingList(DianaPrince).get(0).getDate().before(contactManager.getPastMeetingList(DianaPrince).get(1).getDate()));
	}

	//getPastMeetingList
	@Test
	public void throwsExceptionContactNotExistsPastMeeting() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Contact does not exist");
		contactManager.getPastMeetingList(ArthurCurry);
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
		thrown.expectMessage("Contacts is null");
		contactManager.addNewPastMeeting(null, new GregorianCalendar(2014, 00, 23, 12, 00), "No New Business");
	}
	
	//addNewPastMeeting()
	@Test 
	public void throwsExceptionNullCalender() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null item found");
		contactManager.addNewPastMeeting(contacts, null, "No New Business");
	}
	
	//addNewPastMeeting()
	@Test
	public void throwsExceptionNullNotes() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null item found");
		contactManager.addNewPastMeeting(contacts, new GregorianCalendar(2014, 00, 23, 12, 00), null);
	}
	
	@Test
	public void testAddMeetingNotes() {
		contactManager.addMeetingNotes(1, "new meeting Notes");
		assertEquals("new meeting Notes", contactManager.getPastMeetingList(DianaPrince).get(0).getNotes());
	}
	
	//addMeetingNotes()
	@Test
	public void throwsExceptionMeetingNonExistent() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Meeting does not exist");
		contactManager.addMeetingNotes(5, "non existing meeting");
	}
	
	//addMeetingNotes()
	@Test
	public void throwsExceptionFutureDate() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Future Date Found");
		contactManager.addMeetingNotes(2, "date in future");
	}
	
	//addMeetingNotes()
	@Test
	public void throwsExceptionNotesNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null item found");
		contactManager.addMeetingNotes(1, null);
	}

	@Test
	public void testAddNewContact() {
		contactManager.addNewContact("Arthur Curry", "I AM AQUAMAN");
		assertTrue(contactManager.getContacts("Arthur Curry").contains(ArthurCurry));
	}
	
	// addNewContac()
	@Test
	public void throwsExceptionWhenNameIsNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null item found");
		contactManager.addNewContact(null, "not null");
	}
	
	// addNewContac()
	@Test
	public void throwsExceptionWhenNotesIsNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null item found");
		contactManager.addNewContact("not null", null);
	}

	@Test
	public void testGetContactsIntArray() {
		contactManager.addNewContact("Arthur Curry", "I AM AQUAMAN");
		contactManager.addNewContact("Clark Kent", "I AM SUPERMAN");
		contactManager.addNewContact("Bruce Wayne", "I AM BATMAN");
		contactManager.addNewContact("Billy Batson", "I AM SHAZAM");
		Set<Contact> testSet = contactManager.getContacts(2,3,4,5);
		assertTrue(testSet.contains(ArthurCurry));
		assertTrue(testSet.contains(ClarkKent));
		assertTrue(testSet.contains(BruceWayne));
		assertTrue(testSet.contains(BillyBatson));
	}
	
	//getContacts(int... ids)
	@Test 
	public void throwsExceptionInvalidId() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("One or more id's are invalid");
		contactManager.getContacts(25);
	}

	@Test
	public void testGetContactsString() {
		contactManager.addNewContact("Arthur Curry", "I AM AQUAMAN");
		assertTrue(contactManager.getContacts("Arthur Curry").contains(ArthurCurry));
		assertEquals(1, contactManager.getContacts("Arthur Curry").size());
	}
	
	// getContacts()
	@Test
	public void throwsExceptionWhenGetContactsParameterIsNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Null item found");
		String name = null;
		contactManager.getContacts(name);
	}

	@Test
	public void testFlush() {
		contactManager.flush();
		File file = new File("contacts.txt");
		assertTrue(file.exists());
		try{
			file.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testChronologicalReArrange(){
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 20, 10, 00));
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 28, 10, 00));
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 01, 23, 10, 00));
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2015, 10, 23, 10, 00));
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 04, 23, 10, 00));
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2015, 03, 23, 10, 00));
		contactManager.addFutureMeeting(contacts, new GregorianCalendar(2016, 00, 23, 20, 00));
		List<Meeting> checkList = contactManager.getFutureMeetingList(DianaPrince);
		String checkstring = "";
		for (Meeting m : checkList){
			checkstring = checkstring + m.getId() + " ";
		}
		assertEquals("8 6 3 2 9 4 5 7 ", checkstring);
	}
	
}