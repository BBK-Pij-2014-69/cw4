package cw4;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MeetingImplTest {
	Meeting meeting;
	Set<Contact> contacts;
	Calendar date;
 
	@Before
	public void setUp() {
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
		fail("Not yet implemented");
	}

	@Test
	public void testGetContacts() {
		fail("Not yet implemented");
	}

}
