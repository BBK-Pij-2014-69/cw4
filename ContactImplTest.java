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
		fail("Not yet implemented");
	}

	@Test
	public void testAddNotes() {
		fail("Not yet implemented");
	}

}
