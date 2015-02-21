package cw4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContactImplTest {
	public Contact testContact;

	@Before
	public void setup(){
		testContact = new ContactImpl(1234, "John Jones");
	}
	
	@After
	public void takeDown(){
		testContact = null;
	}
	
	@Test
	public void testGetId() {
		assertEquals(1234, testContact.getId());
	}

	@Test
	public void testGetName() {
		assertEquals("John Jones", testContact.getName());
	}

	@Test
	public void testGetNotes() {
		assertEquals("", testContact.getNotes());
	}

	@Test
	public void testAddNotes() {
		fail("Not yet implemented");
	}

}
