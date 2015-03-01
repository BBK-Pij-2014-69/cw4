package cw4;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting {
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private String notes;
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
		this.notes = "";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Calendar getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
