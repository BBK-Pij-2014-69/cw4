package cw4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl implements FutureMeeting, Serializable {
	
	private static final long serialVersionUID = -5265186606559772231L;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
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

}
