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
		assertEquals(testContact.getId(), 1234);
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
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
