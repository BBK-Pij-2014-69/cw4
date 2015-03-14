package cw4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting, Serializable {

	private static final long serialVersionUID = -5543468645348781558L;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;

	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
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
