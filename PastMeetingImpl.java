package cw4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting, Serializable {

	private static final long serialVersionUID = 4031950374020931191L;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private String notes;
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
		this.notes = notes;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}

	@Override
	public String getNotes() {
		return notes;
	}

}
