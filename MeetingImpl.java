package cw4;

import java.util.Calendar;
import java.util.Set;

/**
 * 
 * @see Meeting
 * @author Kieren Millar
 *
 */
public class MeetingImpl implements Meeting{

	private int id;//id uniqueness is not controlled in this class, please implement if needed.
	private Calendar date;
	private Set<Contact> contacts;

	/**
	 * Constructor for this meeting.
	 * @param id
	 * @param date
	 * @param contacts
	 */
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}

}
